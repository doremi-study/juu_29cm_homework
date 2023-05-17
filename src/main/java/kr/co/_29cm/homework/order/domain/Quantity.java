package kr.co._29cm.homework.order.domain;

import kr.co._29cm.homework.exception.SoldOutException;
import lombok.Getter;

@Getter
public class Quantity {
	private int quantity;

	public Quantity(int quantity) {
		if (quantity < 0) {
			throw new IllegalArgumentException("재고 수량은 0개 이상이여야 합니다.");
		}
		this.quantity = quantity;
	}

	public Quantity subtractPurchaseQuantity(Quantity purchaseQuantity) {
		if (quantity - purchaseQuantity.getQuantity() < 0) {
			throw new SoldOutException("재고가 부족합니다.");
		}

		return new Quantity(quantity - purchaseQuantity.getQuantity());
	}

	public Boolean isAvailablePurchaseQuantity(Quantity purchaseQuantity) {
		return quantity - purchaseQuantity.getQuantity() >= 0;
	}
}
