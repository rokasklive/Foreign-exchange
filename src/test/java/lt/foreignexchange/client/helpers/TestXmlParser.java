package lt.foreignexchange.client.helpers;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import lt.foreignexchange.client.model.Currency;

public class TestXmlParser {

	@Test
	public void shouldReturnListOfObjectsWhenStringIsValid() throws BeansException, SAXException, IOException {
		//Given
		boolean redFlag = false;
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://old.lb.lt//webservices/fxrates/FxRates.asmx/getFxRatesForCurrency?tp=eu&ccy=aud&dtFrom=2018-01-01&dtTo=2019-01-01";
		String response = restTemplate.getForObject(url, String.class);
		
		//When
		String validCurrencyCode = "aud";
		
		//Then
		
		for(Currency c: XmlParser.xmlToObject(response)) {
			if(!c.getCurrencyCode().equalsIgnoreCase(validCurrencyCode)) {
				redFlag = true;
			}
		}
		Assert.assertTrue(!redFlag);
	}

}
