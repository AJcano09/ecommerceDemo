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
	private Long id;
	
	@Column(name="CAT_TITLE" ,length=250 ,nullable =false)
	private String title;
	
	@Column(name="CAT_DESCRIPTIONS" ,length=250 ,nullable =false)
	private String descriptions;
	
	
	public Category() {
		
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

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}	
	
}
