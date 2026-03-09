package com.abner.forms_api_poc.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.abner.forms_api_poc.entities.field.FieldBase;
import com.abner.forms_api_poc.entities.field.FieldNumber;
import com.abner.forms_api_poc.entities.field.FieldText;
import com.abner.forms_api_poc.entities.form.Form;
import com.abner.forms_api_poc.entities.form.FormStatus;
import com.abner.forms_api_poc.entities.form.exceptions.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class FormTest {

  @Test
  void shouldCreateForm() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("k", "l", true));

    Form newform = Form.create(title, status, fields);

    assertEquals(title, newform.title);
    assertEquals(status, newform.status);
  }

  @Test
  void shouldCreatePublishedForm() {
    String title = "titulo";
    FormStatus status = FormStatus.PUBLISHED;
    List<FieldBase> fields = List.of(FieldText.create("k", "l", true));

    Form newform = Form.create(title, status, fields);

    assertEquals(title, newform.title);
    assertEquals(status, newform.status);
    assertNotNull(newform.publishedAt);
  }

  @Test
  void shouldNotCreateFormClosed() {
    String title = "titulo";
    FormStatus status = FormStatus.CLOSED;
    List<FieldBase> fields = List.of(FieldText.create("k", "l", true));

    assertThrows(EnsureNotClosedException.class, () -> Form.create(title, status, fields));
  }

  @Test
  void shouldNotCreateFormWithoutFields() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = new ArrayList<>();

    assertThrows(MinFieldsException.class, () -> Form.create(title, status, fields));
  }

  @Test
  void shouldNotCreateFormWith101Fields() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldNumber.create("key", "label", true, 1, 2));
    Form form = Form.create(title, status, fields);

    var newFields =
        IntStream.range(0, 99)
            .mapToObj(i -> FieldText.create("key" + i, "label" + i, true))
            .toList();
    newFields.forEach(form::addField);

    assertThrows(
        MaxFieldsException.class,
        () -> form.addField(FieldNumber.create("key", "label", true, 1, 2)));
  }

  @Test
  void shouldNotCreateFormWithDuplicatedKey() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("key", "label", true));

    Form newForm = Form.create(title, status, fields);

    assertThrows(
        ConflictFieldsException.class,
        () -> newForm.addField(FieldText.create("key", "label3", true)));
  }

  @Test
  void shouldNotCreateFormWithDuplicatedLabel() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("key", "label", true));

    Form newForm = Form.create(title, status, fields);

    assertThrows(
        ConflictFieldsException.class,
        () -> newForm.addField(FieldText.create("key2", "label", true)));
  }

  @Test
  void shouldNotUpdateFormWhenIsNotDraft() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("key", "label", true));

    Form newForm = Form.create(title, status, fields);
    newForm.status = FormStatus.CLOSED;

    assertThrows(ForbiddenStatusException.class, () -> newForm.update("novo titulo", null));

    newForm.status = FormStatus.PUBLISHED;
    assertThrows(ForbiddenStatusException.class, () -> newForm.update("novo titulo", null));
  }

  @Test
  void shouldUpdateTitle() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("key", "label", true));

    Form newForm = Form.create(title, status, fields);
    Form updatedForm = newForm.update("novo titulo", null);

    assertEquals("novo titulo", updatedForm.title);
  }

  @Test
  void shouldUpdateFormFields() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("key", "label", true));

    Form newForm = Form.create(title, status, fields);
    List<FieldBase> newFields = List.of(FieldText.create("key2", "label2", true));

    Form updatedForm = newForm.update(null, newFields);

    assertEquals(newFields, updatedForm.fields);
  }

  @Test
  void shouldPublishForm() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("key", "label", true));

    Form form = Form.create(title, status, fields);

    form.publish();

    assertEquals(FormStatus.PUBLISHED, form.status);
    assertNotNull(form.publishedAt);
  }

  @Test
  void shouldNotPublishClosedForm() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("key", "label", true));

    Form form = Form.create(title, status, fields);

    form.close();
    assertThrows(ForbiddenStatusException.class, form::publish);
  }

  @Test
  void shouldNotPublishFormTwoTimes() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("key", "label", true));

    Form form = Form.create(title, status, fields);

    form.publish();
    Instant firstPublish = form.publishedAt;

    form.publish();

    assertEquals(form.publishedAt, firstPublish);
  }

  @Test
  void shouldCloseForm() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("key", "label", true));

    Form form = Form.create(title, status, fields);

    form.close();

    assertEquals(FormStatus.CLOSED, form.status);
    assertNotNull(form.closedAt);
  }

  @Test
  void shouldNotCloseFormTwoTimes() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("key", "label", true));

    Form form = Form.create(title, status, fields);

    form.close();
    Instant firstClose = form.closedAt;

    assertEquals(FormStatus.CLOSED, form.status);
    assertEquals(form.closedAt, firstClose);
  }
}
