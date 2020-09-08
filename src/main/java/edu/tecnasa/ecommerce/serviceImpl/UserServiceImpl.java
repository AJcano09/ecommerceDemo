package edu.tecnasa.ecommerce.serviceImpl;

import java.util.ArrayList;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.tecnasa.ecommerce.dao.IUserDAO;
import edu.tecnasa.ecommerce.entities.ClaimType;
import edu.tecnasa.ecommerce.entities.User;


@Service
public class UserServiceImpl implements UserDetailsService {

	@Inject
	private IUserDAO UserDAO;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	
		User currentUser = UserDAO.findByuserName(userName);
		if(currentUser == null) {
			throw new UsernameNotFoundException("User not found");			
		}
		
		//create granted authority
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		if(currentUser.getClaims() != null) {
			for(ClaimType claimType :currentUser.getClaims()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(claimType.getClaimTypeName()));
			}
		}
		
		UserDetails user = new org.springframework.security.core.userdetails.User(userName,
				currentUser.getUserPassword(),
				grantedAuthorities);
		
		return user;		
	}

}
