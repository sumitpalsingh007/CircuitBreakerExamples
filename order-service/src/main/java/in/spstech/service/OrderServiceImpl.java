package in.spstech.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import in.spstech.model.Catalogue;

@Service
public class OrderServiceImpl implements OrderService {

	private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${catalogue.resource.host}")
	private String catalogueResourceHost;

	@Value("${catalogue.resource.port}")
	private String catalogueResourcePort;

	@Autowired
	private CircuitBreakerFactory<?,?> circuitBreakerFactory;

	/*
	 * Fetch from Catalogue Service
	 */

	@Override
	public List<Catalogue> fetchCatalogueService() throws URISyntaxException {
		List<Catalogue> catalogueList = null;

		URI catalogueUri = new URI("http://" + catalogueResourceHost + ":" + catalogueResourcePort + "/catalogue");

		try {
			ResponseEntity<Catalogue[]> catalogueResponse = restTemplate.getForEntity(catalogueUri, Catalogue[].class);
			Catalogue[] catalogue = catalogueResponse.getBody();

			if (catalogueResponse.getStatusCode().is2xxSuccessful()) {
				catalogueList = Arrays.asList(catalogue);
			} else {
				logger.info("No response or Error from [CATALOGUE SERVICE]");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return catalogueList;
	}


	@Override
	public ResponseEntity<Catalogue[]> fetchCatalogueServiceCircuitBreaker() throws URISyntaxException {
		ResponseEntity<Catalogue[]> catalogueRespone = null;
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
		
		URI catalogueUri = new URI("http://" + catalogueResourceHost + ":" + catalogueResourcePort + "/catalogue");

		try {
			catalogueRespone= circuitBreaker.run(() -> restTemplate.getForEntity(catalogueUri, Catalogue[].class), throwable -> getDefaultCatalogueList());
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return catalogueRespone;
	}

	private ResponseEntity<Catalogue[]> getDefaultCatalogueList() {
		logger.info("******* Calling Fallback API **********");
		Catalogue[] response = new Catalogue[1];
		Catalogue catalogue = new Catalogue();
		catalogue.setId(1L);
		catalogue.setName("Dummy Product");
		catalogue.setPrice(0.00);
		response[0] = catalogue;
		return ResponseEntity.of(Optional.of(response));
	}
}
