package jarkz.lab10.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Set;

import com.strobel.decompiler.Decompiler;
import com.strobel.decompiler.DecompilerSettings;
import com.strobel.decompiler.PlainTextOutput;

import jarkz.lab10.types.Output;

public class ClassDecompiler {

	private final DecompilerSettings settings;
	private final String outputDirectory;

	public ClassDecompiler(DecompilerSettings settings, Class<?> mainClass) {
		if (settings == null)
			throw new NullPointerException("DecompilerSettings cannot be null");
		if (mainClass == null)
			throw new NullPointerException("Class cannot be null");
		this.settings = settings;
		outputDirectory = getOuputDirectory(mainClass.getProtectionDomain().getCodeSource().getLocation().getPath());
	}

	public void decompile(Set<String> paths) throws ClassDecompileException, FileNotFoundException {
		if (paths == null)
			throw new NullPointerException("Paths cannot be null");

		for (String classpath : paths) {
			String filepath = classpath.replaceAll(".class$", ".java");
			File file = new File(outputDirectory + "/" + filepath);
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new FileNotFoundException(e.getMessage());
			}

			try (FileOutputStream fos = new FileOutputStream(file);
					OutputStreamWriter writer = new OutputStreamWriter(fos)) {
				Decompiler.decompile(classpath, new PlainTextOutput(writer), settings);
			} catch (IOException e) {
				throw new ClassDecompileException(e.getMessage());
			}
		}
	}

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
