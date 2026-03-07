package com.abner.forms_api_poc.exceptions;

import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidation(
      MethodArgumentNotValidException ex, WebRequest request) {
    String message =
        ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .reduce((a, b) -> a + ", " + b)
            .orElse("Validation failed");

    ErrorResponse error =
        new ErrorResponse(
            "VALIDATION_ERROR", message, request.getDescription(false), Instant.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleMessageNotReadable(
      HttpMessageNotReadableException ex, WebRequest request) {
    String message = "Valor inválido para um dos campos";

    ErrorResponse error =
        new ErrorResponse("INVALID_REQUEST", message, request.getDescription(false), Instant.now());
    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex, WebRequest request) {
    ErrorResponse error =
        new ErrorResponse(
            "CONFLICT", ex.getMessage(), request.getDescription(false), Instant.now());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequest(
      BadRequestException ex, WebRequest request) {
    ErrorResponse error =
        new ErrorResponse(
            "BAD_REQUEST", ex.getMessage(), request.getDescription(false), Instant.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ErrorResponse> handleForbidden(ForbiddenException ex, WebRequest request) {
    ErrorResponse error =
        new ErrorResponse(
            "FORBIDDEN", ex.getMessage(), request.getDescription(false), Instant.now());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, WebRequest request) {
    ErrorResponse error =
        new ErrorResponse(
            "NOT_FOUND", ex.getMessage(), request.getDescription(false), Instant.now());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex, WebRequest request) {
    ErrorResponse error =
        new ErrorResponse(
            "BUSINESS_ERROR", ex.getMessage(), request.getDescription(false), Instant.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, WebRequest request) {
    ErrorResponse error =
        new ErrorResponse(
            "INTERNAL_ERROR",
            "Erro interno do servidor",
            request.getDescription(false),
            Instant.now());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
