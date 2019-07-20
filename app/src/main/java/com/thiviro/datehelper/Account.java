package com.thiviro.datehelper;

import java.util.List;

public class Account {
  private Person user;
  private String id;
  private String imageURL;

  private List<Question> questions;
  private List<Comment> comments;

  public Account (Person person, String id, String imageURL) {
    this.user = person;
    this.id = id;
    this.imageURL = imageURL;
  }
  public Gender getGender() {
    return user.getGender();
  }

  public String getId() {
    return id;
  }
  public String getName() {
    return user.getFullName();
  }
  public String getImageURL() {
    return imageURL;
  }
}
