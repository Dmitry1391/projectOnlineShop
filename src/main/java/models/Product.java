package models;

import java.io.Serializable;

/**
 * This class describes the Product object.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public class Product implements Serializable {

	private Integer id;
	private String name;
	private Double price;

	public Product() {
	}

	public Product(String name, Double price) {
		this.name = name;
		this.price = price;
	}

	public Product(Integer id, String name, Double price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		if (price == null)
			this.price = null;
		else
			this.price = price;
	}


	@Override
	public String toString() {
		return "Product: id = " + id + ", name = " + name + ", price = " + price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result += prime * id.hashCode();
		result += prime * name.hashCode();
		result += prime * price.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}
}