package kr.co._29cm.homework.order.domain;

import java.math.BigDecimal;
import java.util.List;

import kr.co._29cm.homework.exception.SoldOutException;
import lombok.Getter;

@Getter
public class OrderProcessor {
	private static final int MINIMUM_FREE_SHIPPING_PRICE = 5000;
	private static final int DEFAULT_SHIPPING_PRICE = 2500;

	public synchronized static OrderReceipt process(List<Order> orders) {
		boolean availablePlaceOrder = OrderProcessor.isAvailablePlaceOrder(orders);
		if (availablePlaceOrder) {
			purchase(orders);
		} else {
			fail();
		}

		BigDecimal totalProductPrice = totalProductPrice(orders);
		BigDecimal totalOrderPrice = totalOrderPrice(totalProductPrice);
		return new OrderReceipt(orders, totalProductPrice, totalOrderPrice);
	}

	private static void fail() {
		throw new SoldOutException();
	}

	private static boolean isAvailablePlaceOrder(List<Order> orders) {
		for (Order order : orders) {
			Quantity currentQuantity = order.getProduct().getQuantity();
			Quantity purchaseQuantity = order.getQuantity();
			boolean isAvailable = currentQuantity.isAvailablePurchaseQuantity(purchaseQuantity);
			if (!isAvailable) {
				return false;
			}
		}
		return true;
	}

	private static BigDecimal totalOrderPrice(BigDecimal productTotalPrice) {
		int shippingPrice = calculatedDeliveryPrice(productTotalPrice.intValue());
		return productTotalPrice.add(new BigDecimal(shippingPrice));
	}

	private static int calculatedDeliveryPrice(int totalPrice) {
		return totalPrice < MINIMUM_FREE_SHIPPING_PRICE ? DEFAULT_SHIPPING_PRICE : 0;
	}

	private static BigDecimal totalProductPrice(List<Order> orderList) {
		return orderList.stream()
			.map(Order::getProduct)
			.map(Product::getPrice)
			.map(Price::getPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);

	}

	private static void purchase(List<Order> orders) {
		for (Order order : orders) {
			Product product = order.getProduct();
			product.decreaseStockByPurchaseQuantity(order.getQuantity());
		}
	}

}
