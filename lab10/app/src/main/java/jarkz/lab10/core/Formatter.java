package jarkz.lab10.core;

import java.io.File;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jarkz.lab10.types.Output;

/**
 * Formatter can formats java files. Formatter will formats using specific
 * rule.<br>
 * The classes which will formats will be placed in the decompiled output
 * directory.
 * <br>
 * This class using concurrency and uses half of the availables machine
 * cores.<br>
 * Uses the {@link ExecutorService} and {@link JavaCodeFormatter}.
 */
public class Formatter {

	private final FileChannel channel;
	private final String DEFAULT_LOG_FILE = "logFile.txt";

	private class FileChannel {
		private final String inputDirectory;
		private final String outputDirectory;

		/**
		 * Uses the running program absolute path and path must not contains the
		 * packages of running program. For expample, program placed as path
		 * "../my_directory/build/com/domain/.." and need cut to "../my_directory". Also
		 * with jar file "../my_directory/app.jar" will cuts to "../my_directory".
		 *
		 * @param runningProgramPath the absolute path to directory where java program
		 *                           runs.
		 * @throws NullPointerException If runningProgramPath is null
		 */
		private FileChannel(String runningProgramPath) {
			if (runningProgramPath == null) {
				throw new NullPointerException("runningProgramPath cannot be null");
			}
			if (runningProgramPath.endsWith(".jar")) {
				String[] splittedPath = runningProgramPath.split("/");
				runningProgramPath = runningProgramPath.substring(0,
						runningProgramPath.length() - splittedPath[splittedPath.length - 1].length());
			}
			inputDirectory = runningProgramPath + Output.DECOMPILED.getPath();
			outputDirectory = runningProgramPath + Output.REFORMATTED.getPath();
		}

		/**
		 * Gets input directory ends with "../output/decompiled".
		 *
		 * @return input directory as absolute path
		 */
		private String getInput() {
			return inputDirectory;
		}

		/**
		 * Gets output directory ends with "../output/reformatted".
		 *
		 * @return output directory as absolute path
		 */
		private String getOutput() {
			return outputDirectory;
		}
	}

	/**
	 * Creates the Formatter instance and will creates the input/output paths, but
	 * not creates the directories.
	 *
	 * @param mainClass the main class from main package.
	 *
	 * @throws NullPointerException If mainClass is null
	 */
	public Formatter(Class<?> mainClass) {
		if (mainClass == null)
			throw new NullPointerException("Class cannot be null");
		channel = new FileChannel(mainClass.getProtectionDomain().getCodeSource().getLocation().getPath());
	}

	/**
	 * Formats the java files from "/output/decompiled" and write to
	 * "/output/reformatted/".<br>
	 *
	 * @param paths the class paths like "com/domain/test/SomeClass.class". or
	 *              "com/domain/test/SomeClass.java"
	 * 
	 * @throws FormatException       If some errors occurred when java files formats
	 *                               (e.g. program don't have permissions of invalid
	 *                               absolute path)
	 * @throws FileDeletionException If can't delete log file because program don't
	 *                               have permissions.
	 */
	public void format(Set<String> paths) throws FormatException, FileDeletionException {
		format(paths, DEFAULT_LOG_FILE);
	}

	/**
	 * Formats the java files from "/output/decompiled" and write to
	 * "/output/reformatted/".<br>
	 *
	 * @param paths   the class paths like "com/domain/test/SomeClass.class". or
	 *                "com/domain/test/SomeClass.java"
	 * @param logFile overrides the specific path for logfile as String.
	 * 
	 * @throws FormatException       If some errors occurred when java files formats
	 *                               (e.g. program don't have permissions of invalid
	 *                               absolute path)
	 * @throws FileDeletionException If can't delete log file because program don't
	 *                               have permissions.
	 */
	public void format(Set<String> paths, String logFile) throws FormatException, FileDeletionException {
		if (paths == null)
			throw new NullPointerException("Paths cannot be null");
		if (paths.isEmpty())
			throw new IllegalArgumentException("Paths cannot be empty");
		if (logFile == null)
			throw new NullPointerException("logFile cannot be null");
		if (logFile.isBlank())
			throw new IllegalArgumentException("logFile cannot be empty");

		File file = new File(logFile);
		if (file.exists()) {
			boolean deleted = file.delete();
			if (!deleted) {
				throw new FileDeletionException("Can't delete old " + logFile + " log file.");
			}
		}

		int cores = Runtime.getRuntime().availableProcessors();
		cores = cores > 1 ? cores / 2 : cores;
		ExecutorService service = Executors.newFixedThreadPool(cores);

		for (String path : paths) {
			if (path.endsWith(".class$")) {
				path = path.replaceAll(".class$", ".java");
			}
			JavaCodeFormatter formatter = new JavaCodeFormatter(channel.getInput() + "/" + path,
					channel.getOutput() + "/" + path);
			service.execute(formatter);
		}
		service.shutdown();

		checkLogFile(logFile);
	}

	/**
	 * Check for the presence of a log file. If don't exists - program haven't
	 * errors. Otherwise, throw the exceptions.
	 *
	 * @param filename the absolute path to the log file
	 *
	 * @throws FormatException If some errors occurred in formatting
	 */
	private void checkLogFile(String filename) throws FormatException {
		File file = new File(filename);
		if (!file.exists()) {
			return;
		}

		throw new FormatException(
				"An error occurred in formatting. Please check the log file: " + file.getAbsolutePath());
	}
}
