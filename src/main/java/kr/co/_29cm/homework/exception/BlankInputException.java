package kr.co._29cm.homework.exception;

public class BlankInputException extends IllegalArgumentException {
	public BlankInputException() {
		super("빈칸은 입력할 수 없습니다.");
	}
}
