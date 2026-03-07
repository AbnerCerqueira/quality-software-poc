package com.abner.forms_api_poc.entities.submission;

import com.abner.forms_api_poc.entities.Base;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("submissions")
public class Submission extends Base {
  public ObjectId formId;

  public List<AnswerItem> answers;
}
