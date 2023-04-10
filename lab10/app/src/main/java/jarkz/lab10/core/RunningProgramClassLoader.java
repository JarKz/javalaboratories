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

/**
 * Load classes from main packages in which placed main class.
 * <br>
 * Can loads classes from both places: build directory which have .class files
 * and .jar file.
 * <br>
 * Usage:
 * 
 * <pre class="code">
 * var classLoader = new RunningProgramClassLoader(App.class);
 * List<String> classPaths = classLoader.classPaths();
 * </pre>
 */
public class RunningProgramClassLoader {

	private Class<?> mainClass;
	private String runningProgramPath;
	private boolean jar = false;

	/**
	 * Creates class loader using main class.
	 *
	 * @param mainClass the main class that placed in main package.
	 *
	 * @throws NullPointerException If param mainClass is null
	 */
	public RunningProgramClassLoader(Class<?> mainClass) {
		if (mainClass == null)
			throw new NullPointerException("Main class cannot be null");
		this.mainClass = mainClass;
		runningProgramPath = mainClass.getProtectionDomain().getCodeSource().getLocation().getPath();
		if (runningProgramPath.endsWith(".jar")) {
			jar = true;
		}
	}

	/**
	 * Loads class path from running program.
	 *
	 * @return set of path classes like "com/domain/test/App.class"
	 * @throws ClassLoaderException
	 */
	public Set<String> classPaths() throws ClassLoaderException {
		if (jar) {
			return getClassPathsFromJar();
		}
		return getClassPathsFromDirectory(null);
	}

	/**
	 * Loads class paths from build directory.
	 *
	 * @param currentPackage the package for scanning. Pass another package only and
	 *                       only if next package is nested from previous.
	 * @return the set of class path like "com/domain/test/App.class"
	 * @throws ClassLoaderException If classes not founds or program don't have
	 *                              permissions to read files.
	 */
	private Set<String> getClassPathsFromDirectory(String currentPackage) throws ClassLoaderException {
		Set<String> paths = new HashSet<>();
		if (currentPackage == null) {
			currentPackage = mainClass.getPackageName().replaceAll("\\.", "/");
		}
		InputStream stream = mainClass.getClassLoader().getResourceAsStream(currentPackage);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		try {
			while (reader.ready()) {
				String line = reader.readLine();
				if (!line.endsWith(".class")) {
					paths.addAll(getClassPathsFromDirectory(currentPackage + "/" + line));
					continue;
				}

				paths.add(currentPackage + "/" + line);
			}
		} catch (IOException e) {
			throw new ClassLoaderException(e.getMessage(), e.getCause());
		}
		return paths;
	}

	/**
	 * Loads class paths from jar file.
	 *
	 * @return the set of class path like "com/domain/test/App.class"
	 * @throws ClassLoaderException If classes not founds or program don't have
	 *                              permissions to read files.
	 */
	private Set<String> getClassPathsFromJar() throws ClassLoaderException {
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
			throw new ClassLoaderException(e.getMessage(), e.getCause());
		}

		return paths;
	}
}
