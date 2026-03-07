package com.abner.forms_api_poc.exceptions;

public class BadRequestException extends BusinessException {
  public BadRequestException(String message) {
    super(message);
  }
}
