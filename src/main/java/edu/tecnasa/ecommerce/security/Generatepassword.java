package edu.tecnasa.ecommerce.security;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Generatepassword {

	public static void main(String[] args) {
		BCryptPasswordEncoder d = new BCryptPasswordEncoder();
		System.out.println(d.encode("secreto"));
	}

}
