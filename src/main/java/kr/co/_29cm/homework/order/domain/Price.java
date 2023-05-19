package kr.co._29cm.homework.order.domain;

import java.math.BigDecimal;

import kr.co._29cm.homework.util.NumberFormatter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class Price {
	public BigDecimal price;

	public Price(int price) {
		if (price < 0) {
			throw new IllegalArgumentException("금액은 마이너스가 될 수 없습니다.");
		}
		this.price = new BigDecimal(price);
	}

	public Price(BigDecimal price) {
		if (price.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("금액은 마이너스가 될 수 없습니다.");
		}
		this.price = price;
	}

	public String priceWithCommas() {
		return NumberFormatter.formatNumberWithCommas(this.price.intValue());
	}
}
