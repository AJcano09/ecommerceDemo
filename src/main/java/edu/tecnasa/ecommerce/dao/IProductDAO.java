package edu.tecnasa.ecommerce.dao;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import edu.tecnasa.ecommerce.entities.Product;

@Repository
public interface IProductDAO extends PagingAndSortingRepository<Product ,Long>{

	@Query("SELECT p FROM Product p WHERE  p.categories.categoryId = : param1")
	Page<Product> SearchByCategoryId(Pageable pageable ,@Param("param1") Long categoryId);
	
}

