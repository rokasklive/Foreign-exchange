package lt.foreignexchange.client.helpers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import lt.foreignexchange.client.configuration.XmlParserConfig;
import lt.foreignexchange.client.model.Currency;
/**
 * Helper class designed to interpret the response from Bank of Lithuania webservice that are in XML string form. 
 * @author Rokas Kucinskas
 */
public class XmlParser {

	private XmlParser() {}
	
	private static ApplicationContext context = new AnnotationConfigApplicationContext(XmlParserConfig.class);
	
	/**
	 * Converts XML string to and InputStream and using DOM document object model to retrieve required element data that is used
	 * to create Currency objects and stores them on a List of Currency objects.
	 * 
	 * @param response XML string.
	 * @return List<Currency> of currency objects.
	 * @throws SAXException
	 * @throws IOException
	 */
	public static List<Currency> xmlToObject(String response) throws SAXException, IOException{
		List<Currency> currencyResultList = new ArrayList<>(); //A list for currency objects to be parsed from XML.
		InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8)); //Converts a string to InputStream
		Document doc = context.getBean(DocumentBuilder.class).parse(stream); //Parses InputStream to create a Document object
		Element root = doc.getDocumentElement(); //
		NodeList nodes = root.getChildNodes();
		
		for(int i = 0; i < nodes.getLength(); i++) { //Iterating through root element node list and creating objects using data within elements with specified tag names.
			if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element first = (Element) nodes.item(i);
				Currency currency = new Currency();
				currency.setCurrencyCode(first.getElementsByTagName("Ccy").item(1).getTextContent());
				currency.setDate(first.getElementsByTagName("Dt").item(0).getTextContent());
				currency.setRate(first.getElementsByTagName("Amt").item(1).getTextContent());		
				currencyResultList.add(currency); //storing parsed object to list of results.
			}
		}
		return currencyResultList;
	}
}
