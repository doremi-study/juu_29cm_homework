package kr.co._29cm.homework.order.domain;

import java.util.concurrent.atomic.AtomicInteger;

import kr.co._29cm.homework.exception.SoldOutException;

public class Quantity {
	private final AtomicInteger quantity;

	public Quantity(int initialQuantity) {
		quantity = create(initialQuantity);
	}

	public Quantity(String initialQuantity) {
		if (initialQuantity.isEmpty()) {
			throw new IllegalArgumentException("잘못입력하셨습니다.");
		}
		int numberQuantity = Integer.parseInt(initialQuantity);
		quantity = create(numberQuantity);
	}

	private AtomicInteger create(int initialQuantity) {
		final AtomicInteger quantity;
		if (initialQuantity < 0) {
			throw new IllegalArgumentException("재고 수량은 0개 이상이여야 합니다.");
		}
		return new AtomicInteger(initialQuantity);
	}

	public synchronized Quantity subtractPurchaseQuantity(Quantity purchaseQuantity) {
		int subtractedQuantity = purchaseQuantity.getQuantity();
		AtomicInteger currentQuantity = new AtomicInteger(quantity.get());

		int updatedQuantity = currentQuantity.addAndGet(-subtractedQuantity);
		if (updatedQuantity < 0) {
			throw new SoldOutException();
		}

		return new Quantity(updatedQuantity);
	}

	public int getQuantity() {
		return quantity.get();
	}

	// public Boolean isAvailablePurchaseQuantity(Quantity purchaseQuantity) {
	// 	int currentQuantity = quantity.get();
	// 	int subtractedQuantity = purchaseQuantity.getQuantity();
	// 	return currentQuantity - subtractedQuantity >= 0;
	// }

	// public boolean isAvailablePurchaseQuantity(Quantity purchaseQuantity) {
	// 	int currentQuantity = quantity.get();
	// 	int subtractedQuantity = purchaseQuantity.getQuantity();
	// 	int newQuantity = currentQuantity - subtractedQuantity;
	//
	// 	return quantity.getAndDecrement() >= subtractedQuantity;
	// }


	public boolean isAvailablePurchaseQuantity(Quantity purchaseQuantity) {
		int subtractedQuantity = purchaseQuantity.getQuantity();
		AtomicInteger currentQuantity = new AtomicInteger(quantity.get());

		int updatedQuantity = currentQuantity.addAndGet(-subtractedQuantity);
		return updatedQuantity >= 0;
	}
}
