package kr.co._29cm.homework.order.domain;

import kr.co._29cm.homework.util.NumberFormatter;
import lombok.Getter;

@Getter
public class ProductNumber {
	private final String productNumber;

	public ProductNumber(int productNumber) {
		this.productNumber = NumberFormatter.lpadNumber(productNumber);
	}
}
