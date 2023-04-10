package jarkz.lab10.core;

/**
 * Signals that the files can't delete by some reason.
 */
public class FileDeletionException extends Exception {

	public FileDeletionException() {
		super();
	}

	public FileDeletionException(String message) {
		super(message);
	}

	public FileDeletionException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileDeletionException(Throwable cause) {
		super(cause);
	}
}
