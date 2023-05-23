package kr.co._29cm.homework.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import kr.co._29cm.homework.exception.InvalidInputException;
import kr.co._29cm.homework.exception.SoldOutException;
import kr.co._29cm.homework.domain.Order;
import kr.co._29cm.homework.domain.OrderLine;
import kr.co._29cm.homework.domain.OrderProcessor;
import kr.co._29cm.homework.domain.OrderReceipt;
import kr.co._29cm.homework.domain.Product;

public class OrderManager {

	private static final String CONTINUE_ORDER = "o";
	private static final String QUIT_ORDER = "q";
	private static final String QUESTION_PRODUCT_NUMBER = "상품번호:";
	private static final String QUESTION_QUANTITY = "수량:";
	private static final String QUESTION_CONTINUE_ORDER = "입력(o[order]: 주문, q[quit]: 종료):";

	private final List<Product> products;
	private final Scanner scanner;

	public OrderManager(List<Product> products, Scanner scanner) {
		this.products = products;
		this.scanner = scanner;
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
			System.out.println(QUESTION_PRODUCT_NUMBER);
			String productNumber = scanner.nextLine();

			System.out.println(QUESTION_QUANTITY);
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
		while (true) {
			System.out.println(QUESTION_CONTINUE_ORDER);
			String userChoice = scanner.nextLine();

			try {
				if (CONTINUE_ORDER.equals(userChoice)) {
					return true;
				} else if (QUIT_ORDER.equals(userChoice)) {
					return false;
				} else {
					throw new InvalidInputException();
				}
			} catch (InvalidInputException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
