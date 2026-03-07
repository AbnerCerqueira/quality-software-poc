package com.abner.forms_api_poc.entities.form.exceptions;

import com.abner.forms_api_poc.exceptions.NotFoundException;

public class FormNotFoundException extends NotFoundException {
  public FormNotFoundException() {
    super("Formulário não encontrado");
  }
}
