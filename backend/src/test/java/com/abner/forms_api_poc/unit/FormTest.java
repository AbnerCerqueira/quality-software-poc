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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FormTest {

  @Test
  @DisplayName("happy path: cria formulário válido")
  void shouldCreateForm() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("k", "l", true));

    assertDoesNotThrow(() -> Form.create(title, status, fields));
  }

  @Test
  @DisplayName("exception: adiciona campo em formulário publicado")
  void shouldAddFieldInForm() {
    String title = "titulo";
    FormStatus status = FormStatus.PUBLISHED;
    List<FieldBase> fields = List.of(FieldText.create("k", "l", true));

    Form newform = Form.create(title, status, fields);

    FieldText newField = FieldText.create("k2", "l2", true);
    assertThrows(ForbiddenStatusException.class, () -> newform.addField(newField));
  }

  @Test
  @DisplayName("funcional: cria form já publicado")
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
  @DisplayName("edge: não cria form fechado")
  void shouldNotCreateFormClosed() {
    String title = "titulo";
    FormStatus status = FormStatus.CLOSED;
    List<FieldBase> fields = List.of(FieldText.create("k", "l", true));

    assertThrows(EnsureNotClosedException.class, () -> Form.create(title, status, fields));
  }

  @Test
  @DisplayName("exception: quantidade mínima de campos")
  void shouldNotCreateFormWithoutFields() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = new ArrayList<>();

    assertThrows(MinFieldsException.class, () -> Form.create(title, status, fields));
  }

  @Test
  @DisplayName("exception: quantidade máxima de campos")
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
  @DisplayName("exception: campos duplicados")
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
  @DisplayName("exception: campos duplicados por label")
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
  @DisplayName("exception: atualizar form quando não está em DRAFT")
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
  @DisplayName("funcional: atualizar título")
  void shouldUpdateTitle() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("key", "label", true));

    Form newForm = Form.create(title, status, fields);
    Form updatedForm = newForm.update("novo titulo", null);

    assertEquals("novo titulo", updatedForm.title);
  }

  @Test
  @DisplayName("funcional: atualizar campos")
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
  @DisplayName("funcional: publicar form")
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
  @DisplayName("exception: publicar form fechado")
  void shouldNotPublishClosedForm() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("key", "label", true));

    Form form = Form.create(title, status, fields);

    form.close();
    assertThrows(ForbiddenStatusException.class, form::publish);
  }

  @Test
  @DisplayName("edge: publicar form que já está publicado")
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
  @DisplayName("funcional: fechar form")
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
  @DisplayName("edge: fechar form duas vezes")
  void shouldNotCloseFormTwoTimes() {
    String title = "titulo";
    FormStatus status = FormStatus.DRAFT;
    List<FieldBase> fields = List.of(FieldText.create("key", "label", true));

    Form form = Form.create(title, status, fields);

    form.close();
    Instant firstClose = form.closedAt;
    form.close();

    assertEquals(FormStatus.CLOSED, form.status);
    assertEquals(form.closedAt, firstClose);
  }
}
