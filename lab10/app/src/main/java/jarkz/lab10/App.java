package jarkz.lab10;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import com.strobel.decompiler.DecompilerSettings;

import jarkz.lab10.core.ClassDecompiler;
import jarkz.lab10.core.FileDeletionException;
import jarkz.lab10.core.FormatException;
import jarkz.lab10.core.Formatter;
import jarkz.lab10.core.RunningProgramClassLoader;

public class App {

	public static void main(String[] args) {
		RunningProgramClassLoader loader = new RunningProgramClassLoader(App.class);
		ClassDecompiler decompiler = new ClassDecompiler(DecompilerSettings.javaDefaults(), App.class);
		Formatter formatter = new Formatter(App.class);
		try {
			Set<String> paths = loader.classPaths();
			decompiler.decompile(paths);
			paths = paths.stream().map(p -> p.replaceAll(".class$", ".java")).collect(Collectors.toSet());
			formatter.format(paths);
		} catch (FileDeletionException e) {
			e.printStackTrace();
		} catch (FormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
