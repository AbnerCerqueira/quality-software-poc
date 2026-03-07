package com.abner.forms_api_poc.entities.form.http.dtos;

import com.abner.forms_api_poc.entities.field.FieldBase;
import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldNumberRequest;
import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldRequest;
import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldSelectRequest;
import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldTextRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public record ConcatFields() {

  public static List<FieldBase> execute(
      List<CreateFieldTextRequest> textFields,
      List<CreateFieldNumberRequest> numberFields,
      List<CreateFieldSelectRequest> selectFields) {
    List<CreateFieldTextRequest> assertedTextFields =
        textFields != null ? textFields : Collections.emptyList();
    List<CreateFieldNumberRequest> assertedNumberFields =
        numberFields != null ? numberFields : Collections.emptyList();
    List<CreateFieldSelectRequest> assertedSelectFields =
        selectFields != null ? selectFields : Collections.emptyList();

    return Stream.concat(
            Stream.concat(
                assertedTextFields.stream().map(CreateFieldRequest::toDomain),
                assertedNumberFields.stream().map(CreateFieldRequest::toDomain)),
            assertedSelectFields.stream().map(CreateFieldRequest::toDomain))
        .toList();
  }
}
