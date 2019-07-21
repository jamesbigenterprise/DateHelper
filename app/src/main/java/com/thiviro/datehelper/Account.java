package com.thiviro.datehelper;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Account {
  private Person user;
  private String firstName;
  private String lastName;
  private String id;
  private String gender;
  private String studyArea;
  @SerializedName("photoURL")
  private String imageURL;

  private List<Question> questions;
  private List<Comment> comments;

  public Account (Person person, String id, String imageURL) {
    this.user = person;
    this.id = id;
    this.imageURL = imageURL;
    this.firstName = person.getFirstName();
    this.lastName = person.getLastName();
    this.gender = person.getGender().toString();
    this.studyArea = "";
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
  public String getStudyArea() {return studyArea;}
  public void setStudyArea(String studyArea){ this.studyArea = studyArea;}
}
