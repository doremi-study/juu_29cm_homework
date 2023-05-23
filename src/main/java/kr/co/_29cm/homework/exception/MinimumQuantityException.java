package kr.co._29cm.homework.exception;

public class MinimumQuantityException extends RuntimeException {
	public MinimumQuantityException() {
		super("재고 수량은 0개 이상이여야 합니다.");
	}
}
