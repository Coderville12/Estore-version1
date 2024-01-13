package com.gp.electro.store.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gp.electro.store.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {

		logger.info("Exception handler invoked!!!");
		return new ResponseEntity<ApiResponse>(
				ApiResponse.builder().message(ex.getMessage()).status(true).httpStatus(HttpStatus.NOT_FOUND).build(),
				HttpStatus.NOT_FOUND);
	}

	// MethodArgumentNotValidException handler
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>>methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
		Map<String, Object> response = new HashMap<String, Object>();
		allErrors.stream().forEach(objectError -> {
			String message = objectError.getDefaultMessage();
			String field = ((FieldError) objectError).getField();
			response.put(field, message);
		});
		logger.info("Exception handler invoked ---->for methodArgumentNotvallid!!!");
		// return new ResponseEntity<Map<String,Object>>(
		// ApiResponse.builder().message(ex.getMessage()).status(true).httpStatus(HttpStatus.NOT_ACCEPTABLE).build(),
		// HttpStatus.NOT_ACCEPTABLE);
	return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BadRequest.class)
	public ResponseEntity<ApiResponse> badRequestExceptionHandler(BadRequest ex) {

		logger.info("Exception handler invoked- for bad request!!");
		return new ResponseEntity<ApiResponse>(
				ApiResponse.builder().message(ex.getMessage()).status(false).httpStatus(HttpStatus.BAD_REQUEST).build(),
				HttpStatus.BAD_REQUEST);
	}

}
