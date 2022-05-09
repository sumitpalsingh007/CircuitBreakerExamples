package in.spstech.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import in.spstech.model.Catalogue;
import in.spstech.repository.CatalogueRepository;

@RestController
public class CatalogueController {
	private static final Logger LOG = Logger.getLogger(CatalogueController.class.getName()); 
	
	@Autowired
	private CatalogueRepository catalogueRepository;

    @GetMapping("/catalogue")
    public Object fetchProducts ()
    {
        List<Catalogue> products = catalogueRepository.findAll();
        LOG.log(Level.INFO, "you called fetchProducts");
        return products;
    }

}
