package kr.co._29cm.homework.exception;

public class InvalidInputException extends IllegalArgumentException {
	public InvalidInputException() {
		super("잘못된 입력입니다. 다시 입력해주세요.");
	}
}
