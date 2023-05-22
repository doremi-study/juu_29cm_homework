package kr.co._29cm.homework.order.domain;

import kr.co._29cm.homework.util.NumberFormatter;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter @EqualsAndHashCode
public class ProductNumber {
	private final String productNumber;

	public ProductNumber(int productNumber) {
		this.productNumber = NumberFormatter.lpadNumber(productNumber);
	}

	public ProductNumber(String productNumber) {
		if (productNumber.isEmpty()) {
			throw new IllegalArgumentException("잘못입력하셨습니다.");
		}

		int number = Integer.parseInt(productNumber);
		this.productNumber = NumberFormatter.lpadNumber(number);

	}
}
