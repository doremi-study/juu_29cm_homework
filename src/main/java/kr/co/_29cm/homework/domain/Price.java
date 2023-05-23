package kr.co._29cm.homework.domain;

import java.math.BigDecimal;

import kr.co._29cm.homework.exception.NegativePriceException;
import kr.co._29cm.homework.util.NumberFormatter;

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

	public BigDecimal getPriceAsBigDecimal() {
		return price;
	}
}
