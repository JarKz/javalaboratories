package jarkz.lab10.core;

/**
 * Signals that some errors occurred during decompiling of classes.
 */
public class ClassDecompileException extends RuntimeException {

	public ClassDecompileException() {
		super();
	}

	public ClassDecompileException(String message) {
		super(message);
	}

	public ClassDecompileException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClassDecompileException(Throwable cause) {
		super(cause);
	}
}
