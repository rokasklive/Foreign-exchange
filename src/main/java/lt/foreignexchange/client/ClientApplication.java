package lt.foreignexchange.client;

import java.io.IOException;
import java.util.TreeSet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lt.foreignexchange.client.helpers.Utilities;
import lt.foreignexchange.client.service.ForeignExchangeServiceImpl;

@SpringBootApplication
public class ClientApplication {

	
	public static void main(String[] args){
		SpringApplication.run(ClientApplication.class, args);
		
		ForeignExchangeServiceImpl service = new ForeignExchangeServiceImpl();
		
		service.requestRatesInDateRange(Utilities.fromDateSelector(), 
										Utilities.toDateSelector(), 
										Utilities.currencySelector(new TreeSet<>()));
		
		Utilities.terminateApp();
	
	}
}

