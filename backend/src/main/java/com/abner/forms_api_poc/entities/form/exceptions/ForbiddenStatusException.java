package com.abner.forms_api_poc.entities.form.exceptions;

import com.abner.forms_api_poc.exceptions.ForbiddenException;

public class ForbiddenStatusException extends ForbiddenException {
  public ForbiddenStatusException(String message) {
    super(message);
  }
}
