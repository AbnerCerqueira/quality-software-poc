package com.abner.forms_api_poc.entities.field;

import java.util.List;

public class FieldSelect extends FieldBase {
  public List<String> options;

  private FieldSelect(String key, String label, Boolean required, List<String> options) {
    super(key, label, required);
    this.options = options;
  }

  public static FieldSelect create(
      String key, String label, Boolean required, List<String> options) {
    return new FieldSelect(key, label, required, options);
  }

  @Override
  public FieldType getType() {
    return FieldType.SELECT;
  }

  @Override
  public Object getContent() {
    return new Content(this.options);
  }

  @Override
  public void validateAnswer(Object value) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public record Content(List<String> options) {}
}
