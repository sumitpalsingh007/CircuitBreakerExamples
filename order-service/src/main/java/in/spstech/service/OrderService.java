package in.spstech.service;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import in.spstech.model.Catalogue;

public interface OrderService {

	List<Catalogue> fetchCatalogueService() throws URISyntaxException;
	
	ResponseEntity<Catalogue[]> fetchCatalogueServiceCircuitBreaker() throws URISyntaxException;

}
