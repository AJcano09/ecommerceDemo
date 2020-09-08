package edu.tecnasa.ecommerce.security;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import edu.tecnasa.ecommerce.serviceImpl.UserServiceImpl;



@EnableWebSecurity
public class SecurityConfiguration {

	@Configuration
    @Order(1)                                                        
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Inject
		private UserServiceImpl userDetailsService;
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http.antMatcher("/api/**")
				.authorizeRequests(
						authorize -> authorize
                    .anyRequest().authenticated()).httpBasic()
				.realmName("API");
			
			http.csrf().disable();
	
			http.cors();
        }
    }
	
	@Configuration
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Inject
		private UserServiceImpl userDetailsService;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// http://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/AntPathMatcher.html

			http.headers().cacheControl().disable();

			// para evitar error al cargar archivos con el control de prime-faces
			// Refused to display ... in a frame because it set 'X-Frame-Options' to 'deny'.
			http.headers().frameOptions().sameOrigin();

			http.csrf().disable();

			http.authorizeRequests()
				.antMatchers("/javax.faces.resource/**").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/**/favicon.ico").permitAll()
				.antMatchers("/manifest.json").permitAll()
				.antMatchers("/service-worker.js").permitAll()
				.anyRequest().authenticated()
				
				.and()
					.formLogin().loginPage("/login.xhtml").permitAll()
					.failureUrl("/login.xhtml?error=true")
					.defaultSuccessUrl("/index.xhtml")
				
				.and()
					.logout()
					.invalidateHttpSession(true)
					.logoutSuccessUrl("/login.xhtml");

		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		}
	}

}
