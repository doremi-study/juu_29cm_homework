package kr.co._29cm.homework.order.domain;

import lombok.Getter;

@Getter
public class Product {
	private final ProductNumber productNumber;
	private final String productName;
	private final Price price;
	private Quantity quantity;

	public Product(int productNumber, String productName, int price, int quantity) {
		this.productNumber = new ProductNumber(productNumber);
		this.productName = productName;
		this.price = new Price(price);
		this.quantity = new Quantity(quantity);
	}

	public void updateQuantity(Quantity remainingQuantity) {
		this.quantity = remainingQuantity;
	}
}
