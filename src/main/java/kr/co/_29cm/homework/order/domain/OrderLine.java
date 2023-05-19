package kr.co._29cm.homework.order.domain;

import java.math.BigDecimal;
import java.util.List;

import kr.co._29cm.homework.exception.SoldOutException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
public class OrderLine {

	public List<Order> orders;
	private final Price shippingPrice;
	private final Price productTotalPrice;
	private final Price finalTotalPrice;

	public static OrderLine placeOrder(List<Order> orders) {
		OrderProcessor orderProcessor = new OrderProcessor(orders);
		orderProcessor.checkBeforePlaceOrder();
		orderProcessor.purchase();
		BigDecimal productTotalPrice = orderProcessor.calculateTotalPrice(orders);
		int shippingPrice = orderProcessor.calculateDeliveryPrice(productTotalPrice.intValue());
		BigDecimal finalPrice = productTotalPrice.add(new BigDecimal(shippingPrice));

		return OrderLine.builder()
			.orders(orders)
			.shippingPrice(new Price(shippingPrice))
			.productTotalPrice(new Price(productTotalPrice))
			.finalTotalPrice(new Price(finalPrice))
			.build();
	}

	public void resultDisplay() {
		System.out.println("-----------------------------------");
		for (Order order : orders) {
			String productName = order.getProduct().getProductName();
			int quantity = order.getPurchaseQuantity().getQuantity();

			System.out.println(productName + " - " + quantity + "개");
		}

		System.out.println("-----------------------------------");
		System.out.println("주문금액 : " + this.productTotalPrice.priceWithCommas() + "원");
		System.out.println("-----------------------------------");
		System.out.println("지불금액 : " + this.finalTotalPrice.priceWithCommas() + "원");
		System.out.println("-----------------------------------");
	}

}
