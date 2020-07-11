package com.example.tanmayee.message;

public class ResponseMessage {
  
	private String message;
	private long fileSize;
	
	
	public ResponseMessage(String message, long fileSize) {
		this.message = message;
		this.fileSize = fileSize;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	
	
}
