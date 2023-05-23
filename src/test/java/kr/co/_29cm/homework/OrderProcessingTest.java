package kr.co._29cm.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import kr.co._29cm.homework.domain.Order;
import kr.co._29cm.homework.domain.OrderLine;
import kr.co._29cm.homework.domain.OrderReceipt;
import kr.co._29cm.homework.domain.Orders;
import kr.co._29cm.homework.domain.Product;
import kr.co._29cm.homework.exception.SoldOutException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderProcessingTest {
	private List<Product> products;

	@BeforeEach
	void setUp() {
		products = new ArrayList<>();
		products.add(new Product(768848, "[STANLEY] GO CERAMIVAC 진공 텀블러/보틀 3종", 21000, 45));
		products.add(new Product(748943, "디오디너리 데일리 세트 (Daily set)", 19000, 89));
		products.add(new Product(779989, "버드와이저 HOME DJing 굿즈 세트", 35000, 43));
		products.add(new Product(779943, "Fabrik Pottery Flat Cup & Saucer - Mint", 24900, 89));
		products.add(new Product(768110, "네페라 손 세정제 대용량 500ml 이더블유지", 7000, 79));
		products.add(new Product(517643, "에어팟프로 AirPods PRO 블루투스 이어폰(MWP22KH/A)", 260800, 26));
		products.add(new Product(706803, "ZEROVITY™ Flip Flop Cream 2.0 (Z-FF-CRAJ-)", 38000, 81));
		products.add(new Product(759928, "마스크 스트랩 분실방지 오염방지 목걸이", 2800, 85));
		products.add(new Product(213341, "20SS 오픈 카라/투 버튼 피케 티셔츠 (6color)", 33250, 99));
		products.add(new Product(377169, "[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)", 24900, 60));
		products.add(new Product(744775, "SHUT UP [TK00112]", 28000, 35));
		products.add(new Product(779049, "[리퍼브/키친마켓] Fabrik Pottery Cup, Saucer (단품)", 10000, 64));
		products.add(new Product(611019, "플루크 new 피그먼트 오버핏 반팔티셔츠 FST701 / 7color M", 19800, 7));
		products.add(new Product(628066, "무설탕 프로틴 초콜릿 틴볼스", 12900, 8));
		products.add(new Product(502480, "[29Edition.]_[스페셜구성] 렉시 브라렛 세트(브라1+팬티2)", 24900, 41));
		products.add(new Product(782858, "폴로 랄프로렌 남성 수영복반바지 컬렉션 (51color)", 39500, 50));
		products.add(new Product(760709, "파버카스텔 연필1자루", 200, 70));
		products.add(new Product(778422, "캠핑덕 우드롤테이블", 45000, 7));
		products.add(new Product(648418, "BS 02-2A DAYPACK 26 (BLACK)", 238000, 5));
	}

	@AfterAll
	public void tearDown() {
		System.out.println("-----------------------------------------------");
		// 모든 스레드 작업 완료 후 재고 상황 확인
		for (Product product : products) {
			System.out.println(
				"상품번호: " + product.getProductNumber().getProductNumber() + ", 상품이름: " + product.getProductName() + ", 남은 수량: " + product.getQuantity().getQuantityAsInt()
					+ "개");
		}
		System.out.println("-----------------------------------------------");
	}

	@Test
	public void testOrderProcessing() {
		int numThreads = 6; // 동시에 실행할 스레드 수

		ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

		for (int i = 0; i < numThreads; i++) {
			executorService.submit(() -> {
				try {

					List<OrderLine> orderLines = new ArrayList<>();
					orderLines.add(new OrderLine("782858", "10"));

					Orders orders = Orders.placeOrder(orderLines, products);
					OrderReceipt orderReceipt = OrderReceipt.create(orders);
					// 처리된 주문 목록 및 재고 상황 확인
					for (Order order : orderReceipt.getOrders().getList()) {
						System.out.println("이름: " + order.getProduct().getProductName() + ", 남은 수량: " + order.getProduct().getQuantity().getQuantityAsInt() + "개");
					}

				} catch (SoldOutException exception) {
					System.out.println(exception.getMessage());
				}

			});
		}

		executorService.shutdown();
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
