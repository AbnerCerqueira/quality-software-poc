package com.abner.forms_api_poc.entities.form.http;

import com.abner.forms_api_poc.entities.form.Form;
import com.abner.forms_api_poc.entities.form.http.dtos.FormDTO;
import com.abner.forms_api_poc.entities.form.usecases.PublishFormUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishFormController extends FormController {
  private final PublishFormUseCase useCase;

  public PublishFormController(PublishFormUseCase useCase) {
    this.useCase = useCase;
  }

  @PatchMapping("/publish/{id}")
  public ResponseEntity<FormDTO> handle(@PathVariable String id) {
    Form publishedForm = this.useCase.execute(id);

    return publishedForm == null
        ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(FormDTO.fromDomain(publishedForm));
  }
}
