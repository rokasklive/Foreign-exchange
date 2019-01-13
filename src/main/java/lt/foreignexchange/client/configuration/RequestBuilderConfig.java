package lt.foreignexchange.client.configuration;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestBuilderConfig {

	@Bean
	public URIBuilder getRequestBuilder() {
		
		URIBuilder requestBuilder = new URIBuilder();
		requestBuilder.setScheme("http")
					  .setHost("old.lb.lt");		
		return requestBuilder;
	}
	
}
