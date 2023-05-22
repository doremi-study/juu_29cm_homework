package kr.co._29cm.homework.order.domain;

import java.util.List;

import lombok.Getter;

@Getter
public class OrderLine {

	private final ProductNumber productNumber;
	private final Quantity quantity;

	public OrderLine(String productNumber, String quantity) {
		this.productNumber = new ProductNumber(productNumber);
		this.quantity = new Quantity(quantity);
	}

	private Product findProducts(List<Product> products) {
		return products.stream().filter(v -> v.equalProductNumber(productNumber)).findFirst().orElseThrow(() -> new IllegalArgumentException("제품을 찾을 수 없습니다."));
	}

	public Order toOrder(List<Product> products) {
		Product product = findProducts(products);
		return new Order(product, quantity);
	}

}
