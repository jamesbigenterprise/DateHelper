package com.thiviro.datehelper;

import java.util.List;

public class Account {
  private Person user;
  private String id;
  private List<Question> questions;
  private List<Comment> comments;

  public Account (Person person, String id) {
    this.user = person;
    this.id = id;
  }
  public boolean getGender() {
    return user.getGender();
  }

  public String getId() {
    return id;
  }
}
