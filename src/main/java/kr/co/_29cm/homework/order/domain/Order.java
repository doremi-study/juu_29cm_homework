package kr.co._29cm.homework.order.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Order {
	private final Product product;
	private final Quantity quantity;

	public Order(Product product, Quantity quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public static List<Order> placeOrder(List<OrderLine> orderLines, List<Product> products) {
		List<Order> orders = new ArrayList<>();
		for (OrderLine orderLine : orderLines) {
			Order order = orderLine.toOrder(products);
			orders.add(order);
		}
		return orders;
	}

}
