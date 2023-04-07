package jarkz.lab10.core;

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
