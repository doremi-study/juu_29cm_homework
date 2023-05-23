package kr.co._29cm.homework.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Orders {

	private final List<Order> orders;

	private static final int MINIMUM_FREE_SHIPPING_PRICE = 5000;
	private static final int DEFAULT_SHIPPING_PRICE = 2500;

	public Orders(List<Order> orders) {
		this.orders = orders;
	}

	public static Orders placeOrder(List<OrderLine> orderLines, List<Product> products) {
		List<Order> orders = new ArrayList<>();
		for (OrderLine orderLine : orderLines) {
			Order order = Order.toOrder(products, orderLine);
			orders.add(order);
		}
		return new Orders(orders);
	}

	public BigDecimal getProductPriceTotal() {
		return orders.stream()
			.map(Order::getProduct)
			.map(Product::getPrice)
			.map(Price::getPriceAsBigDecimal)
			.reduce(BigDecimal.ZERO, BigDecimal::add);

	}

	public List<Order> getList() {
		return orders;
	}

	public BigDecimal getOrderPriceTotal() {
		BigDecimal productPriceTotal = getProductPriceTotal();
		int shippingPrice = productPriceTotal.intValue() < MINIMUM_FREE_SHIPPING_PRICE ? DEFAULT_SHIPPING_PRICE : 0;
		return productPriceTotal.add(new BigDecimal(shippingPrice));
	}

	public boolean isAvailablePlaceOrder() {
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

	public void purchase() {
		for (Order order : orders) {
			Product product = order.getProduct();
			product.decreaseStockByPurchaseQuantity(order.getQuantity());
		}
	}
}
