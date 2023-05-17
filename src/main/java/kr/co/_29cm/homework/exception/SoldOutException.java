package kr.co._29cm.homework.exception;

public class SoldOutException extends RuntimeException {

	public SoldOutException() {
		super();
	}

	public SoldOutException(String message) {
		super(message);
	}
}
