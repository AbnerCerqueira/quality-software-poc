package com.abner.forms_api_poc.entities.field;

import com.abner.forms_api_poc.entities.field.exceptions.EmptyKeyOrLabelException;

public abstract class FieldBase {
  public String key;

  public String label;

  public Boolean required;

  protected FieldBase(String key, String label, Boolean required) {
    this.key = key;
    this.label = label;
    this.required = required;

    if (this.key.trim().isEmpty() || this.label.trim().isEmpty()) {
      throw new EmptyKeyOrLabelException("Key ou Label do campo está vazio");
    }
  }

  public abstract FieldType getType();

  public abstract Object getContent();

  public abstract void validateAnswer(Object value);
}
