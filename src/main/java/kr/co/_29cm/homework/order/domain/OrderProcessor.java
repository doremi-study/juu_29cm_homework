package kr.co._29cm.homework.order.domain;

import java.util.List;

import kr.co._29cm.homework.exception.SoldOutException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class OrderProcessor {
	private static final int MINIMUM_FREE_SHIPPING_PRICE = 5000;
	private static final int DEFAULT_SHIPPING_PRICE = 2500;

	private List<Order> orders;

	public int calculateDeliveryPrice(int totalPrice) {
		return totalPrice < MINIMUM_FREE_SHIPPING_PRICE ? DEFAULT_SHIPPING_PRICE : 0;
	}

	public int calculateTotalPrice(List<Order> orderList) {
		return orderList.stream()
			.mapToInt(order -> order.getProduct().getPrice().getPrice())
			.sum();
	}

	public void checkBeforePlaceOrder() {
		for (Order order : orders) {
			Quantity currentQuantity = order.getProduct().getQuantity();
			Quantity purchaseQuantity = order.getPurchaseQuantity();

			boolean isAvailable = currentQuantity.isAvailablePurchaseQuantity(purchaseQuantity);

			if (!isAvailable) {
				throw new SoldOutException("[" + order.getProduct().getProductName() + "] 해당 상품의 재고가 부족합니다. ");
			}
		}
	}

	public void purchase() {
		for (Order order : orders) {
			Product product = order.getProduct();
			Quantity purchaseQuantity = order.getPurchaseQuantity();
			Quantity remainingQuantity = product.getQuantity().subtractPurchaseQuantity(purchaseQuantity);
			//Quantity remainingQuantity = product.getQuantity().subtractPurchaseQuantity(purchaseQuantity);
			product.updateQuantity(remainingQuantity);
		}
	}
}
