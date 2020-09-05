package edu.tecnasa.ecommerce.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.tecnasa.ecommerce.entities.Category;

@Repository
public interface ICategoryDAO extends PagingAndSortingRepository<Category ,Long>{

	
}
