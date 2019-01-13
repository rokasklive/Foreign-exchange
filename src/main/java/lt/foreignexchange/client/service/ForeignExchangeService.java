package lt.foreignexchange.client.service;

import java.util.Set;

public interface ForeignExchangeService {

	public void requestRatesInDateRange(String dateFrom, String dateTo, Set<String> currencies);
	public void compileResults(String dateFrom, String dateTo);
	
}
