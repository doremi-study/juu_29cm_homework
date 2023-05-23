프로젝트 구조
- Domain
- 주의했던 포인트
- 모든 원시값과 문자열 포장
- Product 내부의 Price, ProductNumber, Quantity를 객체로 포장했다.
- console로 값을 받아야 하기 때문에 각 객체 내에서 생성자로 생성할 때 Integer나 string 모두 들어와도 변환 가능하게 생성자를 세팅하였고, 기본적인 빈값 체크들이나 금액이나 수량의 경우 음수 체크를 진행하였다.

- 일급컬렉션 사용
- 최초에 만들땐 Order의 경우 OrderList들의 기능을 관리하는 OrderProcessor를 만들었으나, Order List를 포장한 Orders 를 만들면서 OrderProcessor 는 제거하였다. 주문 목록들의 금액 합이나, 주문 과정은 Orders를 통해 계산되었다.

- 동시성 보장
- 멀티스레드 상황에서 동시성을 보장하기 위해 Quantity의 경우 AtomicInteger로 변환하였다.


- OrderReceipt 는 영수증이자 영수증으로서 구매 가능할 경우 주문내역을 발행할 수 있고, 재고가 없을 경우 SoldOutException을 발생시킨다.

- Service
  최대한 로직은 객체의 역할에 맞게 객체 안에 분배했고, 나머지 입력하면서 필요한 로직들은 OrderManager로 분리해서 진행했다.

- Exception
- Exception의 경우, System.out.println 로 콘솔에 찍는게 제일 편한 방법이였지만, 실무에서는 Exception 처리를 하기 때문에 Exception을 만들어놓고,
  허용가능한 에러가 발생해도 계속 진행될 수 있도록 try catch 로 잡아서 error message를 출력하게 변경하였다.

- 실행
```java
	public static void main(String[] args) {
	    order();
	}
```
Main.class 에 실행 가능한 두가지 method를 만들어서 넣어놨다.
order(); 의 경우 
