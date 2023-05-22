package kr.co._29cm.homework.exception;

public class NegativePriceException extends IllegalArgumentException {
	public NegativePriceException() {
		super("금액은 마이너스가 될 수 없습니다.");
	}
}
