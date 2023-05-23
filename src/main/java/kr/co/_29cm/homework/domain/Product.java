package kr.co._29cm.homework.domain;

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

	public void decreaseStockByPurchaseQuantity(Quantity purchaseQuantity) {
		this.quantity = this.getQuantity().subtractPurchaseQuantity(purchaseQuantity);
	}

	public boolean equalProductNumber(ProductNumber productNumber) {
		return this.productNumber.equals(productNumber);
	}
}
