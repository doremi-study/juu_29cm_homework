package kr.co._29cm.homework.order.domain;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;

public class OrderReceipt {
	@Getter
	private final List<Order> orders;
	private final Price productTotalPrice;
	private final Price finalTotalPrice;

	public OrderReceipt(List<Order> orders, BigDecimal productTotalPrice, BigDecimal finalTotalPrice) {
		this.orders = orders;
		this.productTotalPrice = new Price(productTotalPrice);
		this.finalTotalPrice = new Price(finalTotalPrice);
	}

	public void print() {
		System.out.println("-----------------------------------");
		for (Order order : orders) {
			String productName = order.getProduct().getProductName();
			int quantity = order.getQuantity().getQuantity();

			System.out.println(productName + " - " + quantity + "개");
		}

		System.out.println("-----------------------------------");
		System.out.println("주문금액 : " + this.productTotalPrice.priceWithCommas() + "원");
		System.out.println("-----------------------------------");
		System.out.println("지불금액 : " + this.finalTotalPrice.priceWithCommas() + "원");
		System.out.println("-----------------------------------");
	}

	public void ss() {
		for (Order order : orders) {
			System.out.println("이름 : " + order.getProduct().getProductName() + ", 남은 수량 :" + order.getProduct().getQuantity().getQuantity() + " 개");
		}
	}
}