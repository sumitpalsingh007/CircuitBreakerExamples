package in.spstech.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Catalogue")
public class Catalogue {
	
	@Id
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @Column
    private Double price;
    
    public Catalogue() {
		super();
	}

    public Catalogue(String name, Double price) {
        this.name = name;
        this.price = price;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Catalogue [id=" + id + ", name=" + name + ", price=" + price + "]";
	}
    
}