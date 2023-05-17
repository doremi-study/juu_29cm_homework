package kr.co._29cm.homework.order.domain;

import java.util.List;

import kr.co._29cm.homework.exception.SoldOutException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OrderLine {

	private static int MINIMUM_FREE_SHIPPING_PRICE = 5000;
	private static int DEFAULT_SHIPPING_PRICE = 2500;


	public List<Order> orders;
	private final int shippingPrice;
	private final int productTotalPrice;
	private final int finalTotalPrice;


	public static OrderLine placeOrder(List<Order> orders) {

		// 사전 재고 체크 로직
		// error 안남

		for (Order order : orders) {
			Quantity currentQuantity = order.getProduct().getQuantity();
			Quantity purchaseQuantity = order.getPurchaseQuantity();

			boolean isAvailable = currentQuantity.isAvailablePurchaseQuantity(purchaseQuantity);

			if (!isAvailable) {
				throw new SoldOutException("[" + order.getProduct().getProductName() + "] 해당 상품의 재고가 부족합니다. ");
			}
		}


		// 구매

		for (Order order : orders) {
			Product product = order.getProduct();
			Quantity purchaseQuantity = order.getPurchaseQuantity();
			Quantity remainingQuantity = product.getQuantity().subtractPurchaseQuantity(purchaseQuantity);
			product.updateQuantity(remainingQuantity);
		}


		int calculateTotalPrice = calculateTotalPrice(orders);
		int deliveryPrice1 = calculateDeliveryPrice(calculateTotalPrice);
		int finalPrice = calculateTotalPrice + deliveryPrice1;
		return OrderLine.builder()
			.orders(orders)
			.shippingPrice(deliveryPrice1)
			.productTotalPrice(calculateTotalPrice)
			.finalTotalPrice(finalPrice)
			.build();

	}

	private static int calculateDeliveryPrice(int totalPrice) {
		return totalPrice < MINIMUM_FREE_SHIPPING_PRICE ? DEFAULT_SHIPPING_PRICE : 0;
	}

	public static int calculateTotalPrice(List<Order> orderList) {
		return orderList.stream()
			.mapToInt(order -> order.getProduct().getPrice().getPrice())
			.sum();
	}

	public void resultDisplay() {
		System.out.println("-----------------------------------");
		for (Order order : orders) {
			String productName = order.getProduct().getProductName();
			int quantity = order.getPurchaseQuantity().getQuantity();

			System.out.println(productName + " - " + quantity + "개");
		}

		System.out.println("-----------------------------------");
		System.out.println("주문금액 : " + this.getProductTotalPrice());
		System.out.println("-----------------------------------");
		System.out.println("지불금액 : " + this.getFinalTotalPrice());
		System.out.println("-----------------------------------");
	}

}
