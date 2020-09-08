package edu.tecnasa.ecommerce.errors;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import edu.tecnasa.ecommerce.dto.ErrorDetails;


@ControllerAdvice
public class ExceptionManager4Rest {

	@ExceptionHandler({ResourceNotFoundException.class})
	public ResponseEntity<ErrorDetails> manageResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
		
		ErrorDetails errorDetail = new ErrorDetails();
		errorDetail.setTime(new Date().getTime());
		errorDetail.setState(HttpStatus.NOT_FOUND.value());
		errorDetail.setTitle("Resource not found.");
		errorDetail.setDescriptions(e.getMessage());
		errorDetail.setTechMessage(e.getClass().getName());
		
		return new ResponseEntity<ErrorDetails>(errorDetail, null, HttpStatus.NOT_FOUND);
	}
	

	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<ErrorDetails> manageMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
		
		ErrorDetails errorDetail = new ErrorDetails();
		errorDetail.setTime(new Date().getTime());
		errorDetail.setState(HttpStatus.BAD_REQUEST.value());
		errorDetail.setTitle("Validation fails.");
		errorDetail.setDescriptions(e.getMessage());
		errorDetail.setTechMessage(e.getClass().getName());
		
		return new ResponseEntity<ErrorDetails>(errorDetail, null, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorDetails> manageGeneralException(Exception e, HttpServletRequest request) {
		
		ErrorDetails errorDetail = new ErrorDetails();
		errorDetail.setTime(new Date().getTime());
		errorDetail.setState(HttpStatus.BAD_REQUEST.value());
		errorDetail.setTitle("Something is wrong.");
		errorDetail.setDescriptions(e.getMessage());
		errorDetail.setTechMessage(e.getClass().getName());
				
		return new ResponseEntity<ErrorDetails>(errorDetail, null, HttpStatus.BAD_REQUEST);
	}
	
}
