package com.abner.forms_api_poc.exceptions;

public class NotFoundException extends BusinessException {
  public NotFoundException(String message) {
    super(message);
  }
}
