package in.spstech.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import in.spstech.model.Catalogue;

public interface CatalogueRepository extends JpaRepository<Catalogue, Long> {
}
