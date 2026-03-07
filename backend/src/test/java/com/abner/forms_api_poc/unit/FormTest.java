package com.abner.forms_api_poc.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.abner.forms_api_poc.entities.field.FieldBase;
import com.abner.forms_api_poc.entities.field.FieldNumber;
import com.abner.forms_api_poc.entities.field.FieldText;
import com.abner.forms_api_poc.entities.form.Form;
import com.abner.forms_api_poc.entities.form.FormStatus;
import com.abner.forms_api_poc.entities.form.exceptions.ConflictFieldsException;
import com.abner.forms_api_poc.entities.form.exceptions.EnsureNotClosedException;
import com.abner.forms_api_poc.entities.form.exceptions.MaxFieldsException;
import com.abner.forms_api_poc.entities.form.exceptions.MinFieldsException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

public class FormTest {

  @Test
  public void shouldCreateForm() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("k", "l", true));
    Instant publishedAt = Instant.now();

    Form newform = Form.create(title, status, fields, publishedAt);

    assertEquals(title, newform.title);
    assertEquals(status, newform.status);
    assertEquals(publishedAt, newform.publishedAt);
  }

  @Test
  public void shouldNotCreateFormClosed() {
    String title = "titulo";
    FormStatus status = FormStatus.CLOSED;
    List<FieldBase> fields = new ArrayList<>();
    Instant publishedAt = Instant.now();

    assertThrows(
        EnsureNotClosedException.class, () -> Form.create(title, status, fields, publishedAt));
  }

  @Test
  public void shouldNotCreateFormWithoutFields() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = new ArrayList<>();
    Instant publishedAt = Instant.now();

    assertThrows(MinFieldsException.class, () -> Form.create(title, status, fields, publishedAt));
  }

  @Test
  public void shouldNotCreateFormWith101Fields() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldNumber.create("key", "label", true, 1, 2));
    Instant publishedAt = Instant.now();
    Form form = Form.create(title, status, fields, publishedAt);

    var campos =
        IntStream.range(0, 99)
            .mapToObj(i -> FieldText.create("key" + i, "label" + i, true))
            .toList();
    campos.forEach(form::addField);

    assertThrows(
        MaxFieldsException.class,
        () -> form.addField(FieldNumber.create("key", "label", true, 1, 2)));
  }

  @Test
  public void shouldNotCreateFormWithDuplicatedKey() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("key", "label", true));
    Instant publishedAt = Instant.now();

    Form newForm = Form.create(title, status, fields, publishedAt);

    assertThrows(
        ConflictFieldsException.class,
        () -> newForm.addField(FieldText.create("key", "label3", true)));
  }

  @Test
  public void shouldNotCreateFormWithDuplicatedLabel() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("key", "label", true));
    Instant publishedAt = Instant.now();

    Form newForm = Form.create(title, status, fields, publishedAt);

    assertThrows(
        ConflictFieldsException.class,
        () -> newForm.addField(FieldText.create("key2", "label", true)));
  }
}
