package edu.tecnasa.ecommerce.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name="E_USER")
public class User implements Identifiable,Serializable {

	private static final long serialVersionUID = -2187499196758426166L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USE_ID")
	private Long userId	;
	
	@Column(name="USE_NAME" ,length=250 ,nullable =false)
	private String userName;
	
	@Column(name="USE_PASSWORD",length=150 , nullable =false)
	private String userPassword;
	
	@ManyToMany(fetch =FetchType.EAGER)
	@JoinTable(name="E_USER_CLAIM",
	joinColumns= @JoinColumn(name="USE_ID"),
	inverseJoinColumns =@JoinColumn(name="CLA_TYP_NAME"))
	private Set<ClaimType> Claims;
	
	public User() {
		
	}
	
	

	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public String getUserPassword() {
		return userPassword;
	}



	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getUserName() {
		return userName;
	}
	
	public Set<ClaimType> getClaims() {
		return Claims;
	}

	public void setClaims(Set<ClaimType> claims) {
		Claims = claims;
	}
	

	
}
