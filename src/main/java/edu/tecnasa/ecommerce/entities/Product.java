package edu.tecnasa.ecommerce.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="E_PRODUCT")
public class Product implements Serializable , Identifiable{

	private static final long serialVersionUID = -7239833158703595011L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PRO_ID")
	private Long id;
	
	@Column(name="PRO_TITLE",length=50 ,nullable =false)	
	private String title;
	
	@Column(name="PRO_SPECIAL",nullable =false)
	private Boolean special;
	
	@Column(name="PRO_DESCRIPTIONS",length=250,nullable =false)
	private String descriptions;
	
	@Column(name="PRO_PRICE",precision=19 ,scale=2)	
	private BigDecimal  price;
	
	@ManyToOne
	@JoinColumn(name="CAT_ID", nullable =false)
	private Category categories;

	public Product() {
		
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

	public Boolean getSpecial() {
		return special;
	}

	public void setSpecial(Boolean special) {
		this.special = special;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public Category getCategories() {
		return categories;
	}

	public void setCategories(Category categories) {
		this.categories = categories;
	}

	
	
	
}
