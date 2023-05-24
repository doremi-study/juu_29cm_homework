# 29CM 백엔드 과제

## 프로젝트 사양
- JDK 11 
- Gradle 
- lombok, assertJ 추가 

## Domain
### 주의했던 포인트
> 모든 원시값과 문자열 포장

- `Product` 내부의 `Price`, `ProductNumber`, `Quantity`를 객체로 포장했다.
  console로 값을 받아야 하기 때문에 각 객체 내에서 생성자로 생성할 때 Integer나 string 모두 들어와도 변환 가능하게 생성자를 세팅하였고, 기본적인 빈값 체크들이나 금액이나 수량의 경우 음수 체크를 진행하였다.

>일급컬렉션 사용

- 최초에 만들땐 `Order`의 경우 OrderList들의 기능을 관리하는 `OrderProcessor`를 만들었으나, 리팩토링 과정에서 Order List를 포장한 객체인 `Orders` 를 만들면서 `OrderProcessor` 는 제거하였다. 주문 목록들의 금액 합이나, 주문 과정은 `Orders`를 통해 계산되었다.

> 동시성 보장

- 멀티스레드 상황에서 동시성을 보장하기 위해 `Quantity`의 경우 `AtomicInteger`로 변환하였다.
- 실질적으로 주문 재고를 파악하고 수량을 차감하는 OrderReceipt orderReceipt = OrderReceipt.create(orders); 부분에 동시접근을 막기 위해 `synchronized` 를 추가하였다.

- `OrderReceipt` 는 영수증이자 영수증으로서 구매 가능할 경우 주문내역을 발행할 수 있고, 재고가 없을 경우 `SoldOutException` 을 발생시킨다.

## Service
- Service 최대한 로직은 객체의 역할에 맞게 객체 안에 분배했고, 나머지 입력하면서 필요한 로직들은 `OrderManager`로 분리해서 진행했다.

## Exception
- Exception의 경우, `System.out.println` 로 콘솔에 찍는게 제일 편한 방법이였지만, 실무에서는 Exception 처리를 하기 때문에 Exception을 만들어놓고, 허용가능한 에러가 발생해도 계속 진행될 수 있도록 try catch 로 잡아서 error message를 출력하게 변경하였다.

### 실행
```java
public static void main(String[] args) {
    order();
}
```
``` java
@Test
public void testOrderProcessing() {
  int numThreads = 10; // 동시에 실행할 스레드 수

  ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
  AtomicBoolean exceptionCaught = new AtomicBoolean(false);

  for (int i = 0; i < numThreads; i++) {
      executorService.submit(() -> {
          try {

              List<OrderLine> orderLines = new ArrayList<>();
              orderLines.add(new OrderLine("782858", "10"));
              orderLines.add(new OrderLine("768848", "5"));

              Orders orders = Orders.placeOrder(orderLines, products);
              OrderReceipt orderReceipt = OrderReceipt.create(orders);
              ...

          } catch (SoldOutException exception) {
              System.out.println(exception.getMessage());
              exceptionCaught.set(true);
          }

      });
  }

  executorService.shutdown();
  try {
      executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
  } catch (InterruptedException e) {
      e.printStackTrace();
  }

  assertThat(exceptionCaught.get()).isTrue();
}
```
- `Main.class` 에는 실행 코드를 넣어놨고, `OrderProcessingTest.class` 에는 멀티스레드 테스트를 위한 테스트 코드가 위치해있다.

1) `order()`의 경우 Main.class 에서 product 리스트를 setUp 해두고 OrderManager를 호출해 로직을 실행한다.
2) `testOrderProcessing()`의 경우 재고가 50개인 상품을 10개씩 10번 호출하여 5개 이후 나머지 주문에서 `SoldOutException` 이 나는 걸 확인하는 코드이다. 추가적으로 주문 마다 남은 재고 갯수를 print 하는 로직도 추가하였다. 에러 확인을 위해 catch 부분에 에러가 발생했을 경우 `AtomicBoolean`를 true로 바꿔주는 로직을 추가하여 모든 스레드가 끝나고 에러가 발생했는지 확인한다.  