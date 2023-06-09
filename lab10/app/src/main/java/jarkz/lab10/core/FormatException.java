package jarkz.lab10.core;

/**
 * Signals that some errors occurred during formatting.
 */
public class FormatException extends Exception {

	public FormatException() {
		super();
	}

	public FormatException(String message) {
		super(message);
	}

	public FormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public FormatException(Throwable cause) {
		super(cause);
	}
}
