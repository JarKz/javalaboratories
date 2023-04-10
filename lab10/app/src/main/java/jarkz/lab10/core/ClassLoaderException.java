package jarkz.lab10.core;

/**
 * Signals that some errors occurred during loads the classes from classpath.
 */
public class ClassLoaderException extends Exception {

	public ClassLoaderException() {
		super();
	}

	public ClassLoaderException(String message) {
		super(message);
	}

	public ClassLoaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClassLoaderException(Throwable cause) {
		super(cause);
	}
}
