package edu.tecnasa.ecommerce.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.tecnasa.ecommerce.entities.ClaimType;

@Repository
public interface IClaimTypeDAO extends PagingAndSortingRepository<ClaimType ,Long> {

}
