package edu.tecnasa.ecommerce.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="E_PRODUCT")
public class Product implements Serializable , Identifiable{

	private static final long serialVersionUID = -7239833158703595011L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PRO_ID")
	private Long productId;
	
	@Column(name="PRO_TITLE",length=50 ,nullable =false)	
	private String productTitle;
	
	@Column(name="PRO_SPECIAL",nullable =false)
	private Boolean productSpecial;
	
	@Column(name="PRO_DESCRIPTIONS",length=250,nullable =false)
	private String productDescriptions;
	
	@Column(name="PRO_PRICE",precision=19 ,scale=2)	
	private BigDecimal  productPrice;
	
	@ManyToOne
	@JoinColumn(name="CAT_ID", nullable =false)
	private Category categories;

	public Product() {
		
	}

	
	public Long getProductId() {
		return productId;
	}


	public void setProductId(Long productId) {
		this.productId = productId;
	}


	public String getProductTitle() {
		return productTitle;
	}


	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}


	public Boolean getProductSpecial() {
		return productSpecial;
	}


	public void setProductSpecial(Boolean productSpecial) {
		this.productSpecial = productSpecial;
	}


	public String getProductDescriptions() {
		return productDescriptions;
	}


	public void setProductDescriptions(String productDescriptions) {
		this.productDescriptions = productDescriptions;
	}


	public BigDecimal getProductPrice() {
		return productPrice;
	}


	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}


	public Category getCategories() {
		return categories;
	}

	public void setCategories(Category categories) {
		this.categories = categories;
	}

	
	
	
}
