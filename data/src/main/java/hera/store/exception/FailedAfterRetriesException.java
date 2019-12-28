package hera.store.exception;

public class FailedAfterRetriesException extends RuntimeException {
	public FailedAfterRetriesException(String errorMessage) {
		super(errorMessage);
	}
}
