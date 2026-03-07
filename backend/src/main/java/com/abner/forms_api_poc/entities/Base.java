package com.abner.forms_api_poc.entities;

import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public abstract class Base {
  @MongoId(FieldType.OBJECT_ID)
  public String id;

  @CreatedDate public Instant createdAt;

  @LastModifiedDate public Instant updatedAt;
}
