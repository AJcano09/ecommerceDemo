package edu.tecnasa.ecommerce.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryDto implements Serializable {


	private static final long serialVersionUID = -4979718564831941392L;

	@JsonProperty("Id")
	private Long id;
	
	@JsonProperty("Title")
	private String title;
	
	@JsonProperty("Description")
	private String description;
	
	@JsonProperty("ImageS")
	private String imageS;
	
	@JsonProperty("ImageL")
	private String imageL;
	
	public CategoryDto() {}
	
	

	public CategoryDto(Long id, String title, String description, String imageS, String imageL) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
