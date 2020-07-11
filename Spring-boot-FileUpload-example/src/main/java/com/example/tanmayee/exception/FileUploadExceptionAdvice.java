package com.example.tanmayee.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.tanmayee.message.ResponseMessage;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("File upload failed as file size exceeds the max size allowed.",0));
	}

	
	  @ExceptionHandler(FileUploadException.class) 
	  public  ResponseEntity<ResponseMessage>  handleInvalidFileNameException(FileUploadException ex){
	  
		  return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
				  ResponseMessage("Sorry! Filename contains invalid path sequence ", 0));
	  
	  }
	 

}
