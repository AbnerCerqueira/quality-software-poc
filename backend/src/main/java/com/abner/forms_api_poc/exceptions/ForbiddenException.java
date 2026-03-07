package com.abner.forms_api_poc.exceptions;

public class ForbiddenException extends BusinessException {
  public ForbiddenException(String message) {
    super(message);
  }
}
