package kr.co._29cm.homework.order.domain;

import java.math.BigDecimal;

import kr.co._29cm.homework.exception.NegativePriceException;
import kr.co._29cm.homework.util.NumberFormatter;
import lombok.Getter;

@Getter
public class Price {
	public BigDecimal price;

	public Price(int price) {
		if (price < 0) {
			throw new NegativePriceException();
		}
		this.price = new BigDecimal(price);
	}

	public Price(BigDecimal price) {
		if (price.compareTo(BigDecimal.ZERO) < 0) {
			throw new NegativePriceException();
		}
		this.price = price;
	}

	public String priceWithCommas() {
		return NumberFormatter.formatNumberWithCommas(this.price.intValue());
	}
}
