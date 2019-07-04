package com.thiago;

import java.util.List;

public class Account {
  private Person user;
  private List<Question> questions;
  //private List<Comment> comments;
  //Validation data goes here
  /*************************/

  public Account (Person person) {
    this.user = person;
  }
  public boolean getGender() {
    return user.getGender();
  }

}
