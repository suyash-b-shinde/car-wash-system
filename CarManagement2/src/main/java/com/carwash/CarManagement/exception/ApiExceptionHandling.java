package com.carwash.CarManagement.exception;
 
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
 
@ControllerAdvice 
public class ApiExceptionHandling { 
 
 @ExceptionHandler(value = { ApiRequestException.class }) 
 public ResponseEntity<Object> handleApiException(ApiRequestException e) { 
	 HttpStatus badrequest = HttpStatus.BAD_REQUEST;
	 ApiException apiException = new ApiException(e.getMessage(), badrequest);
	 return new ResponseEntity<>(apiException, badrequest);
 } 
 
//Handles @Valid validation errors
 @ExceptionHandler(MethodArgumentNotValidException.class)
 public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
     Map<String, String> errors = new HashMap<>();
     ex.getBindingResult().getFieldErrors().forEach(error -> 
         errors.put(error.getField(), error.getDefaultMessage())
     );
     return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
 }

}
