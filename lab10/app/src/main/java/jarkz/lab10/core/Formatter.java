package jarkz.lab10.core;

import java.io.File;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jarkz.lab10.types.Output;

public class Formatter {

	private final FileChannel channel;
	private final String DEFAULT_LOG_FILE = "logFile.txt";

	private class FileChannel {
		private final String inputDirectory;
		private final String outputDirectory;

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

		private String getInput() {
			return inputDirectory;
		}

		private String getOutput() {
			return outputDirectory;
		}
	}

	public Formatter(Class<?> mainClass) {
		if (mainClass == null)
			throw new NullPointerException("Class cannot be null");
		channel = new FileChannel(mainClass.getProtectionDomain().getCodeSource().getLocation().getPath());
	}

	public void format(Set<String> paths) throws FormatException, FileDeletionException {
		format(paths, DEFAULT_LOG_FILE);
	}

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
		if (file.exists()){
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

	private void checkLogFile(String filename) throws FormatException {
		File file = new File(filename);
		if (!file.exists()){
			return;
		}

		throw new FormatException("An error occurred in formatting. Please check the log file: " + file.getAbsolutePath());
	}
}
