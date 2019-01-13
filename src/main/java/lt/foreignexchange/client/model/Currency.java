package lt.foreignexchange.client.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * POJO type class to define a Currency object that is used to contain currency code and rate for given date.
 * Also contains a static field that is used to store multiple Currency objects where key is a currency code and 
 * the value contains a List of currency objects that share the same currency code but differ in assigned date and thus
 * the rate.
 * 
 * @author Rokas Kucinskas
 *
 */

public class Currency {
	
	private String currencyCode;
	private LocalDate date;
	private BigDecimal rate;
	private static Map<String, List<Currency>> resultMap = new TreeMap<>();
	
	public Currency() {	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = LocalDate.parse(date);
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = new BigDecimal(rate);
	}
	
	public static Map<String, List<Currency>> getResultMap() {
		return resultMap;
	}
	
	/**
	 * Clears the results map.
	 */
	public static void clearResultMap() {
		resultMap.clear();
	}

	@Override
	public String toString() {
		return "Currency [currencyCode=" + currencyCode + ", date=" + date + ", rate=" + rate + "]";
	}
	
	
	
}
