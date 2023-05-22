package kr.co._29cm.homework.order.domain;

import java.util.List;

import kr.co._29cm.homework.exception.NotFoundProductException;
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
		return products.stream().filter(v -> v.equalProductNumber(productNumber)).findFirst().orElseThrow(NotFoundProductException::new);
	}

	public Order toOrder(List<Product> products) {
		Product product = findProducts(products);
		return new Order(product, quantity);
	}

}
