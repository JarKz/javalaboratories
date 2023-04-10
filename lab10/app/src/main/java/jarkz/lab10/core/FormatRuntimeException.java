package jarkz.lab10.core;

/**
 * Throws when many errors occurred in attempting to safely interrupt thread.
 */
public class FormatRuntimeException extends RuntimeException {

	public FormatRuntimeException() {
		super();
	}

	public FormatRuntimeException(String message) {
		super(message);
	}

	public FormatRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public FormatRuntimeException(Throwable cause) {
		super(cause);
	}
}
