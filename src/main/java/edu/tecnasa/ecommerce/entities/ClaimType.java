package edu.tecnasa.ecommerce.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="E_CLAIM_TYPE")
public class ClaimType implements Serializable{

	private static final long serialVersionUID = 5438170832580919304L;
	
	@Id	
	@Column(name="CLA_TYP_NAME",length=150 ,nullable=false)
	private String  claimType;

	public String getClaimType() {
		return claimType;
	}	
}
