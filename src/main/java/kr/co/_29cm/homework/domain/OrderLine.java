package kr.co._29cm.homework.domain;

import lombok.Getter;

@Getter
public class OrderLine {

	private final ProductNumber productNumber;
	private final Quantity quantity;

	public OrderLine(String productNumber, String quantity) {
		this.productNumber = new ProductNumber(productNumber);
		this.quantity = new Quantity(quantity);
	}
}
