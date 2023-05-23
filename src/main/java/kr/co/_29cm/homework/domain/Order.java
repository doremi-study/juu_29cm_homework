package kr.co._29cm.homework.domain;

import java.util.List;

import kr.co._29cm.homework.exception.NotFoundProductException;
import lombok.Getter;

@Getter
public class Order {
	private final Product product;
	private final Quantity quantity;

	public Order(Product product, Quantity quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public static Order toOrder(List<Product> products, OrderLine orderLine) {
		Product product = findProduct(products, orderLine);
		return new Order(product, orderLine.getQuantity());
	}

	private static Product findProduct(List<Product> products, OrderLine orderLine) {
		return products.stream().filter(v -> v.equalProductNumber(orderLine.getProductNumber()))
			.findFirst()
			.orElseThrow(NotFoundProductException::new);
	}
}
