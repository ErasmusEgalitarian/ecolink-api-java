package com.ecolink.api.config;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleBadFormat(IllegalArgumentException ex) {
		return ResponseEntity.badRequest().body(Map.of(
				"success", false,
				"message", "something whent wrong. Try another format."
			));
	}	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleTooBigFile(IllegalStateException ex) {
		return ResponseEntity.status(413).body(Map.of(
				"success", false,
				"message", "something whent wrong. The file was too big."
			));	
	}
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleVideoWasNotFound(RuntimeException ex) {
		return ResponseEntity.status(404).body(Map.of(
				"success", false,
				"message", "something whent wrong. Video wasn't found."
			));
	}
}
