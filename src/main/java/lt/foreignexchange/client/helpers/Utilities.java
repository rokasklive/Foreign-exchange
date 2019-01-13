package lt.foreignexchange.client.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Set;

import lt.foreignexchange.client.service.ForeignExchangeServiceImpl;
/**
 * Helper class that contains multiple methods that handle validation and user input.
 * @author Rokas Kucinskas
 *
 */
public class Utilities {
	
	private static Scanner in = new Scanner(System.in);
	private static String input;
	private static String dateFrom;
	
	/**
	 * Makes a HTTP GET request to specified URL that returns an XML list of tracked currencies in Bank of Lithuania.
	 * Conducts a search within the list for any matches to supplied currency code from user input.
	 * @param currencyCode Currency code to be matched.
	 * @return TRUE if a match is found, FALSE if a match is not found.
	 */
	private static boolean validateCurrency(String currencyCode) {
		String response = ForeignExchangeServiceImpl.executeRequest("https://old.lb.lt/webservices/FxRates/FxRates.asmx/getCurrentFxRates?tp=eu");
		if(response.contains(currencyCode.toUpperCase())) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Handles user input for currency codes. Uses a do-while loop to allow the user to make multiple inputs.
	 * Validates each input and adds it to a Set of entries.
	 * Exits the loop when nothing is entered.
	 * @param currencies Currency code.
	 * @return Set<String> of currency codes.
	 */
	public static Set<String> currencySelector(Set<String> currencies){
		System.out.println("Enter currency codes: ");
		do {
			input = in.nextLine();
			if(validateCurrency(input)) {
				currencies.add(input.toUpperCase());
			} 
		} while (!input.isEmpty());

		currencies.removeIf(s -> (s.length()==0)); //A precaution to avoid any Set entries that are empty.

		return currencies;		
	}
	
	/**
	 * Handles user input for the start of desired period.
	 * Checks for the validity of entered date and if input is invalid
	 * defaults to current date.
	 * @return a string that represents a date (yyyy-MM-dd).
	 */
	public static String fromDateSelector() {
		System.out.println("Please enter starting date: (press enter to skip)");
		dateFrom = in.nextLine();
		if(dateFrom.isEmpty() || LocalDate.parse(dateFrom).isAfter(LocalDate.now())) {
			System.out.println("Using current date.");
			dateFrom = DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now()).toString();		
		} 
		return dateFrom;
	}
	
	/**
	 * Handles user input for the end of desired period.
	 * Checks for the validity of entered date and if input is invalid
	 * defaults to current date.
	 * @return a string that represents a date (yyyy-MM-dd).
	 */
	public static String toDateSelector() {
		System.out.println("Please enter end date: (press enter to skip)");
		
		do {
			input = in.nextLine();
			if(input.isEmpty()) {
				return dateFrom;
			} else {
				return input;
			}
		} while(!input.isEmpty());
	}

	
}
