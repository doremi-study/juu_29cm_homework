package kr.co._29cm.homework.domain;

import java.util.concurrent.atomic.AtomicInteger;

import kr.co._29cm.homework.exception.BlankInputException;
import kr.co._29cm.homework.exception.MinimumQuantityException;
import kr.co._29cm.homework.exception.SoldOutException;

public class Quantity {
	private final AtomicInteger quantity;

	public Quantity(int initialQuantity) {
		quantity = create(initialQuantity);
	}

	public Quantity(String initialQuantity) {
		if (initialQuantity.isEmpty()) {
			throw new BlankInputException();
		}
		int numberQuantity = Integer.parseInt(initialQuantity);
		quantity = create(numberQuantity);
	}

	public synchronized Quantity subtractPurchaseQuantity(Quantity purchaseQuantity) {
		int subtractedQuantity = purchaseQuantity.getQuantityAsInt();
		AtomicInteger currentQuantity = new AtomicInteger(quantity.get());

		int updatedQuantity = currentQuantity.addAndGet(-subtractedQuantity);
		if (updatedQuantity < 0) {
			throw new SoldOutException();
		}

		return new Quantity(updatedQuantity);
	}

	public int getQuantityAsInt() {
		return quantity.get();
	}

	public boolean isAvailablePurchaseQuantity(Quantity purchaseQuantity) {
		int subtractedQuantity = purchaseQuantity.getQuantityAsInt();
		AtomicInteger currentQuantity = new AtomicInteger(quantity.get());

		int updatedQuantity = currentQuantity.addAndGet(-subtractedQuantity);
		return updatedQuantity >= 0;
	}

	private AtomicInteger create(int initialQuantity) {
		if (initialQuantity < 0) {
			throw new MinimumQuantityException();
		}
		return new AtomicInteger(initialQuantity);
	}
}
