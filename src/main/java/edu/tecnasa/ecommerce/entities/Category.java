package edu.tecnasa.ecommerce.entities;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="E_CATEGORY")
public class Category implements Identifiable ,Serializable{
	
	private static final long serialVersionUID = -7859132183422530158L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name="CAT_ID")
	private Long categoryId;
	
	@Column(name="CAT_TITLE" ,length=250 ,nullable =false)
	private String category_Title;
	
	@Column(name="CAT_DESCRIPTIONS" ,length=250 ,nullable =false)
	private String category_Descriptions;
	
	
	public Category() {
		
	}


	public Long getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}


	public String getCategory_Title() {
		return category_Title;
	}


	public void setCategory_Title(String category_Title) {
		this.category_Title = category_Title;
	}


	public String getCategory_Descriptions() {
		return category_Descriptions;
	}


	public void setCategory_Descriptions(String category_Descriptions) {
		this.category_Descriptions = category_Descriptions;
	}
	
	
}
