package com.abner.forms_api_poc.entities.form.usecases;

import com.abner.forms_api_poc.entities.form.Form;
import com.abner.forms_api_poc.entities.form.FormStatus;
import com.abner.forms_api_poc.entities.form.repositories.FormRepository;
import org.springframework.stereotype.Service;

@Service
public class PublishFormUseCase {
  private final FormRepository formRepository;

  public PublishFormUseCase(FormRepository formRepository) {
    this.formRepository = formRepository;
  }

  public Form execute(String formId) {
    Form form = this.formRepository.findById(formId).orElse(null);

    if (form == null || form.status == FormStatus.PUBLISHED) {
      return null;
    }

    form.publish();

    return this.formRepository.save(form);
  }
}
