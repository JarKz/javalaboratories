package jarkz.lab10.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class RunningProgramClassLoader {

	private Class<?> mainClass;
	private String runningProgramPath;
	private boolean jar = false;

	public RunningProgramClassLoader(Class<?> mainClass) {
		if (mainClass == null)
			throw new NullPointerException("Main class cannot");
		this.mainClass = mainClass;
		runningProgramPath = mainClass.getProtectionDomain().getCodeSource().getLocation().getPath();
		if (runningProgramPath.endsWith(".jar")) {
			jar = true;
		}
	}

	public Set<String> classPaths() throws IOException {
		if (jar) {
			return getClassPathsFromJar();
		}
		return getClassPathsFromDirectory(null);
	}

	private Set<String> getClassPathsFromDirectory(String currentPackage) throws IOException {
		Set<String> paths = new HashSet<>();
		if (currentPackage == null){
			currentPackage = mainClass.getPackageName().replaceAll("\\.", "/");
		}
		InputStream stream = mainClass.getClassLoader().getResourceAsStream(currentPackage);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		while (reader.ready()){
			String line = reader.readLine();
			if (!line.endsWith(".class")){
				paths.addAll(getClassPathsFromDirectory(currentPackage + "/" + line));
				continue;
			}

			paths.add(currentPackage + "/" + line);
		}
		return paths;
	}

	private Set<String> getClassPathsFromJar() throws IOException {
		Set<String> paths = new HashSet<>();

		String mainPackage = mainClass.getPackageName().replaceAll("\\.", "/");

		try (JarFile jarFile = new JarFile(runningProgramPath)) {
			Iterator<JarEntry> entries = jarFile.entries().asIterator();
			while (entries.hasNext()) {
				JarEntry entry = entries.next();
				if (entry.isDirectory() || !entry.getName().startsWith(mainPackage)
						|| !entry.getName().endsWith(".class")) {
					continue;
				}

				paths.add(entry.getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("An error occurred in getting paths from jar file");
		}

		return paths;
	}
}
