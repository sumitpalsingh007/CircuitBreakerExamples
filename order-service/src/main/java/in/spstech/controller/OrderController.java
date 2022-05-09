package in.spstech.controller;

import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import in.spstech.service.OrderService;

/**
 * @author rasrivastava
 * @apiNote Order Micro-Service
 */
@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/catalogues")
	public Object fetchCatalogue() throws URISyntaxException {
		return orderService.fetchCatalogueServiceCircuitBreaker();
	}

}