package edu.tecnasa.ecommerce.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.tecnasa.ecommerce.entities.User;

@Repository
public interface IUserDAO  extends PagingAndSortingRepository<User ,Long> {

 public User findByuserName(String userName);
}
