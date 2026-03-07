package com.abner.forms_api_poc.e2e.config;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.mongodb.MongoDBContainer;

public class MongoDBContainerSingleton {
  @Container private static MongoDBContainer mongoDBContainer;

  public static MongoDBContainer getInstance() {
    if (MongoDBContainerSingleton.mongoDBContainer == null) {
      MongoDBContainerSingleton.mongoDBContainer = new MongoDBContainer("mongo:7");
    }

    return MongoDBContainerSingleton.mongoDBContainer;
  }

  public static String getUrl() {
    return MongoDBContainerSingleton.getInstance().getReplicaSetUrl();
  }
}
