package kr.co._29cm.homework.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
public class Order {

	private final Product product;
	private final Quantity purchaseQuantity;

	public static Order putOrder(Product product, int purchase) {
		Quantity purchaseQuantity = new Quantity(purchase);
		return Order.builder()
			.product(product)
			.purchaseQuantity(purchaseQuantity)
			.build();
	}
}
