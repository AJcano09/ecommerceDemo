package edu.tecnasa.ecommerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.tecnasa.ecommerce.entities.Category;

public class ProductDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5918668523572477638L;

	@JsonProperty("Id")
	private Long id;
	
	@JsonProperty("Titles")
	private String title;
	
	@JsonProperty("Price")
	private BigDecimal price;
	
	@JsonProperty("Special")
	private boolean  special;
	
	@JsonProperty("Description")
	private String description;
	
	
	private Category category;
	
	@JsonProperty("ImageS")
	private String imageS;
	
	@JsonProperty("ImageL")
	private String imageL;
	
	

	public ProductDto(Long id, String title, BigDecimal price, boolean special, String description, Category category,
			String imageS, String imageL) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
		this.special = special;
		this.description = description;
		this.category = category;
		this.imageS = imageS;
		this.imageL = imageL;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public boolean isSpecial() {
		return special;
	}


	public void setSpecial(boolean special) {
		this.special = special;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public String getImageS() {
		return imageS;
	}


	public void setImageS(String imageS) {
		this.imageS = imageS;
	}


	public String getImageL() {
		return imageL;
	}


	public void setImageL(String imageL) {
		this.imageL = imageL;
	}
	
	
	
	
}
