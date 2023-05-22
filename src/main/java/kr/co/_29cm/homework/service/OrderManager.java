package kr.co._29cm.homework.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import kr.co._29cm.homework.exception.SoldOutException;
import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderLine;
import kr.co._29cm.homework.order.domain.OrderProcessor;
import kr.co._29cm.homework.order.domain.OrderReceipt;
import kr.co._29cm.homework.order.domain.Product;

public class OrderManager {

	private final List<Product> products;
	private final Scanner scanner;

	public OrderManager(List<Product> products) {
		this.products = products;
		this.scanner = new Scanner(System.in);
	}

	public void startOrdering() {
		do {
			List<OrderLine> orderLines = createOrderLines();

			List<Order> orders = Order.placeOrder(orderLines, products);
			try {
				OrderReceipt orderReceipt = OrderProcessor.process(orders);
				orderReceipt.print();
			} catch (SoldOutException exception) {
				System.out.println(exception.getMessage());
			}

		} while (askForNewOrder());
	}

	private List<OrderLine> createOrderLines() {
		List<OrderLine> orderLines = new ArrayList<>();
		while (true) {
			System.out.println("상품번호:");
			String productNumber = scanner.nextLine();

			System.out.println("수량:");
			String quantity = scanner.nextLine();

			if (productNumber.isBlank() && quantity.isBlank()) {
				break;
			}

			OrderLine orderLine = new OrderLine(productNumber, quantity);
			orderLines.add(orderLine);
		}
		return orderLines;
	}

	private boolean askForNewOrder() {
		System.out.println("입력(o[order]: 주문, q[quit]: 종료):");
		String userChoice = scanner.nextLine();

		return "o".equals(userChoice);
	}

}
