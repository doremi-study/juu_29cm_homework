package kr.co._29cm.homework.domain;

import java.math.BigDecimal;

import kr.co._29cm.homework.exception.SoldOutException;

public class OrderReceipt {

	private final Orders orders;
	private final Price productTotalPrice;
	private final Price finalTotalPrice;

	public OrderReceipt(Orders orders, BigDecimal productTotalPrice, BigDecimal finalTotalPrice) {
		this.orders = orders;
		this.productTotalPrice = new Price(productTotalPrice);
		this.finalTotalPrice = new Price(finalTotalPrice);
	}

	public synchronized static OrderReceipt create(Orders orders) {
		boolean availablePlaceOrder = orders.isAvailablePlaceOrder();
		if (!availablePlaceOrder) {
			throw new SoldOutException();
		}
		orders.purchase();
		return new OrderReceipt(orders, orders.getProductPriceTotal(), orders.getOrderPriceTotal());
	}

	public void print() {
		System.out.println("-----------------------------------");
		for (Order order : orders.getList()) {
			String productName = order.getProduct().getProductName();
			int quantity = order.getQuantity().getQuantityAsInt();

			System.out.println(productName + " - " + quantity + "개");
		}

		System.out.println("-----------------------------------");
		System.out.println("주문금액 : " + this.productTotalPrice.priceWithCommas() + "원");
		System.out.println("-----------------------------------");
		System.out.println("지불금액 : " + this.finalTotalPrice.priceWithCommas() + "원");
		System.out.println("-----------------------------------");
	}

	public Orders getOrders() {
		return orders;
	}
}
