package com.abner.forms_api_poc.entities.field.http.dtos;

import com.abner.forms_api_poc.entities.field.FieldBase;
import com.abner.forms_api_poc.entities.field.FieldNumber;
import com.abner.forms_api_poc.entities.field.FieldSelect;
import com.abner.forms_api_poc.entities.field.FieldText;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public abstract class CreateFieldRequest {
  @NotBlank(message = "Preencha a chave do campo")
  public String key;

  @NotBlank(message = "Preencha a label do campo")
  public String label;

  @NotNull(message = "Informe se o campo é requirido")
  public Boolean required;

  public static FieldBase toDomain(CreateFieldRequest req) {
    if (req instanceof CreateFieldTextRequest) {
      return FieldText.create(req.key, req.label, req.required);
    }

    if (req instanceof CreateFieldNumberRequest numberReq) {
      return FieldNumber.create(req.key, req.label, req.required, numberReq.min, numberReq.max);
    }

    if (req instanceof CreateFieldSelectRequest selectReq) {
      return FieldSelect.create(req.key, req.label, req.required, selectReq.options);
    }

    throw new IllegalArgumentException("Field type desconhecido" + req.getClass().getSimpleName());
  }
}
