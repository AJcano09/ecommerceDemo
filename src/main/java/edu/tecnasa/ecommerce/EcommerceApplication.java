package edu.tecnasa.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import edu.tecnasa.ecommerce.util.FileStorageProperties;

@SpringBootApplication
@ComponentScan({"edu.tecnasa.ecommerce"})
@Configuration
@EnableConfigurationProperties({	
	FileStorageProperties.class
})
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

}
