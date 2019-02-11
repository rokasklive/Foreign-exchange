package lt.foreignexchange.client.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lt.foreignexchange.client.configuration.RequestBuilderConfig;
import lt.foreignexchange.client.helpers.XmlParser;
import lt.foreignexchange.client.model.Currency;

@Service
public class ForeignExchangeServiceImpl implements ForeignExchangeService {

	ApplicationContext context = new AnnotationConfigApplicationContext(RequestBuilderConfig.class);
	
	private URIBuilder requestBuilder = context.getBean(URIBuilder.class); 
	
	/**
	 * Uses user input to build and execute a HTTP GET request to Bank of Lithuania <a href="http://www.lb.lt/webservices/fxrates/"> webservices</a> 
	 * Prints the results to console.
	 * 
	 * @param dateFrom		String from user input that represents starting date of desired period.
	 * @param dateTo		String from user input that represents conclusion of desired period.
	 * @param currencies 	Set of user inputs that contains one or more currency codes to be 
	 * 						requested from target webservice.
	 *  
	 */
	public void requestRatesInDateRange(String dateFrom, String dateTo, Set<String> currencies) {
		requestBuilder.setPath("/webservices/fxrates/FxRates.asmx/getFxRatesForCurrency");		
		
		for(String c: currencies) {
			requestBuilder.addParameter("tp", "eu")
						  .addParameter("ccy", c)
						  .addParameter("dtFrom", dateFrom)
						  .addParameter("dtTo", dateTo);				
			try {				
				//Uses each currency code from user input for sending HTTP GET requests and then sends the results to parser.
				//Puts parsed result to map.
				Currency.getResultMap().put(c, XmlParser.xmlToObject(executeRequest(requestBuilder.toString())));				
			} catch (Exception e){
				Logger logger = Logger.getLogger("log");
				logger.log(Level.FINER, e.getMessage());
			}

			requestBuilder.clearParameters();
		}
			compileResults(dateFrom, dateTo);
	}
	
	/**
	 * Uses a static map filled with results for each currency to calculate changes and print the results to console.
	 * 
	 * @param dateFrom		String from user input that represents starting date of desired period.
	 * @param dateTo		String from user input that represents conclusion of desired period.
	 */
	
	public void compileResults(String dateFrom, String dateTo) {
		printFullReport();
		if(!dateFrom.equalsIgnoreCase(dateTo)) {	
			System.out.println("\n" + "Selected currencies exchange rate change for the period of \n\n" + dateFrom + " to " + dateTo + ":\n");
			for(String key: Currency.getResultMap().keySet()) {
				double earliestRate = 0;
				double latestRate = 0;
				BigDecimal change;
				
				for(Currency c: Currency.getResultMap().get(key)) {
					//Determines the earliest currency rate entry in results map.
					if(c.getDate().isEqual(LocalDate.parse(dateFrom))) {
						earliestRate = c.getRate().doubleValue();
					} else
					//Determines the latest currency rate entry in results map.
					if(c.getDate().isEqual(LocalDate.parse(dateTo))) {
						latestRate = c.getRate().doubleValue();
					}
				}

				if(latestRate==0){
					latestRate=1;
				}
				//Calculates the percentage of change between the rate at the start and the end of selected period.
				change = BigDecimal.valueOf(((earliestRate - latestRate)/latestRate)*100).setScale(3, RoundingMode.HALF_EVEN);
				System.out.println(key.toUpperCase() + "  " + change + "%");
			}
		}
	}
	
	/**
	 * Collects and prints all the results on the result map.
	 */
	private void printFullReport() {
		for(String key: Currency.getResultMap().keySet()) {
			System.out.printf("\n%-12s %-14s %-12s\n\n", "Currency", "Date", "Rate");
			for(Currency c: Currency.getResultMap().get(key)) {
				System.out.printf("%-12s %-14s %-12s\n", key.toUpperCase(), c.getDate(), c.getRate()+"\n");
			}
		}
	}
	
	/**
	 * Uses a RestTemplate to make a HTTP GET request to specified URL and returns the response as String.
	 * @param url absolute URL for the request to be made.
	 * @return response string
	 */
	public static String executeRequest(String url) {
		 RestTemplate restTemplate = new RestTemplate();
		 return restTemplate.getForObject(url, String.class);
	}
}
