package jarkz.lab10.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Set;

import com.strobel.decompiler.Decompiler;
import com.strobel.decompiler.DecompilerSettings;
import com.strobel.decompiler.PlainTextOutput;

import jarkz.lab10.types.Output;

/**
 * Decompile classes from class path like "com/domain/test/*.class" and put to
 * specific output directory "/output/decompiled".
 * <br>
 * Classes will be placed like
 * class paths (e.g. class from "com/domain/test/SomeClass.class" will be
 * decompiled to "/output/decompiled/com/domain/test/SomeClass.java")
 * <br>
 * Decompiler can decompile classes from build directory or jar file.
 * <br>
 * Usage:
 * 
 * <pre class="code">
 * Set<String> paths = ...; // get class paths like "com/domain/test/SomeClass.class"
 * ClassDecompiler decompiler = new ClassDecompiler(DecompilerSettings.javaDefaults(), App.class);
 * decompiler.decompile(paths);
 * </pre>
 */
public class ClassDecompiler {

	private final DecompilerSettings settings;
	private final String outputDirectory;

	/**
	 * Creates decompiler for build directory or for jar file.
	 *
	 * @param settings  as {@link DecompilerSettings}
	 * @param mainClass the main class from main package.
	 *
	 * @throws NullPointerException If settings or mainClass is null
	 */
	public ClassDecompiler(DecompilerSettings settings, Class<?> mainClass) {
		if (settings == null)
			throw new NullPointerException("DecompilerSettings cannot be null");
		if (mainClass == null)
			throw new NullPointerException("Class cannot be null");
		this.settings = settings;
		outputDirectory = getOuputDirectory(mainClass.getProtectionDomain().getCodeSource().getLocation().getPath());
	}

	/**
	 * Decompile classes from from running program using class path like
	 * "com/domain/test/*.class" and put decompiled classes to output directory
	 * "/output/decompiled/com/domain/test/*" with .java extension.
	 *
	 * @param paths as class path like "com/domain/test/SomeClass.class"
	 *
	 * @throws ClassDecompileException If prograom don't have permissions to
	 *                                 read/write files.
	 * @throws FileCreationException       If can't create file to specific output
	 *                                 directory.
	 */
	public void decompile(Set<String> paths) throws ClassDecompileException, FileCreationException {
		if (paths == null)
			throw new NullPointerException("Paths cannot be null");

		for (String classpath : paths) {
			String filepath = classpath.replaceAll(".class$", ".java");
			File file = new File(outputDirectory + "/" + filepath);
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new FileCreationException(e.getMessage(), e.getCause());
			}

			try (FileOutputStream fos = new FileOutputStream(file);
					OutputStreamWriter writer = new OutputStreamWriter(fos)) {
				Decompiler.decompile(classpath, new PlainTextOutput(writer), settings);
			} catch (IOException e) {
				throw new ClassDecompileException(e.getMessage());
			}
		}
	}

	/**
	 * Creates the output path for decompiled classes. Uses the running program
	 * absolute path and path must not contains the packages of running program. For
	 * expample, program placed as path "../my_directory/build/com/domain/.." and
	 * need cut to "../my_directory". Also with jar file "../my_directory/app.jar"
	 * will cuts to "../my_directory".
	 *
	 * @param runningProgramPath absolute path where the program is runs.
	 *
	 * @return the output directory as absolute path
	 */
	private String getOuputDirectory(String runningProgramPath) {
		if (runningProgramPath == null)
			throw new NullPointerException("runingProgramPath cannot be null");
		if (runningProgramPath.endsWith(".jar")) {
			String[] splittedPath = runningProgramPath.split("/");
			runningProgramPath = runningProgramPath.substring(0,
					runningProgramPath.length() - splittedPath[splittedPath.length - 1].length());
		}
		return runningProgramPath + Output.DECOMPILED.getPath();
	}
}
