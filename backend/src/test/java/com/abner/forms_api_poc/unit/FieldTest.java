package com.abner.forms_api_poc.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.abner.forms_api_poc.entities.field.FieldNumber;
import com.abner.forms_api_poc.entities.field.FieldSelect;
import com.abner.forms_api_poc.entities.field.FieldText;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FieldTest {
  @Test
  @DisplayName("happy path: cria campo texto válido")
  public void shouldCreateTextField() {
    String key = "nome";
    String label = "Qual o seu nome?";
    Boolean required = true;

    FieldText field = FieldText.create(key, label, required);

    assertEquals(key, field.key);
    assertEquals(label, field.label);
    assertEquals(required, field.required);
  }

  @Test
  @DisplayName("happy path: cria campo número válido")
  public void shouldCreateNumberField() {
    String key = "avaliacção";
    String label = "De 0 a 5 como você avalia esta implementação?";
    Boolean required = true;
    Integer min = 0;
    Integer max = 5;

    FieldNumber field = FieldNumber.create(key, label, required, min, max);

    assertEquals(key, field.key);
    assertEquals(label, field.label);
    assertEquals(required, field.required);
    assertEquals(min, field.min);
    assertEquals(max, field.max);
  }

  @Test
  @DisplayName("happy path: cria campo select válido")
  public void shouldCreateSelectField() {
    String key = "localizacao";
    String label = "Qual estado você mora?";
    Boolean required = true;
    List<String> options = List.of("SP", "RJ", "BA");

    FieldSelect field = FieldSelect.create(key, label, required, options);

    assertEquals(key, field.key);
    assertEquals(label, field.label);
    assertEquals(required, field.required);
    assertEquals(options, field.options);
  }
}
