package edu.tecnasa.ecommerce.dto;

public class ErrorDetails {
	
	private String title;
	
	private int state;
	
	private String descriptions;
	
	private long time;	
	
	private String techMessage;


	public ErrorDetails(String title, int state, String descriptions, long time, String thechMessage) {
		super();
		this.title = title;
		this.state = state;
		this.descriptions = descriptions;
		this.time = time;
		this.techMessage = thechMessage;
	}


	public ErrorDetails() {
		// TODO Auto-generated constructor stub
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}


	public String getDescriptions() {
		return descriptions;
	}


	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}


	public long getTime() {
		return time;
	}


	public void setTime(long time) {
		this.time = time;
	}


	public String getTechMessage() {
		return techMessage;
	}


	public void setTechMessage(String thechMessage) {
		this.techMessage = thechMessage;
	}
	
	

}
