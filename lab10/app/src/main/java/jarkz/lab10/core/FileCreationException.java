package jarkz.lab10.core;

/**
 * Signals that file can't creates by some reasons (e.g. program don't have
 * permissions or invalid absolute path)
 */
public class FileCreationException extends Exception {

	public FileCreationException() {
		super();
	}

	public FileCreationException(String message) {
		super(message);
	}

	public FileCreationException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileCreationException(Throwable cause) {
		super(cause);
	}
}
