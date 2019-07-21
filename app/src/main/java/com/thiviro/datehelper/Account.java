package com.thiviro.datehelper;
import com.google.gson.annotations.SerializedName;
import java.util.List;


/**
 * The Account class contains the main properties
 * for a user account in the app. This class is used
 * as a bridge with data coming from the cloud.
 *
 * @author Thiago, Rolando
 *
 */
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

  /**
   * This constructor sets the main properties for and account.
   *
   *
   * @param person    Person abstraction of the owner of the account
   * @param id        Identifier string for the account
   * @param imageURL  String containing a link to the profile picture
   */
  public Account (Person person, String id, String imageURL) {
    this.user = person;
    this.id = id;
    this.imageURL = imageURL;
    this.firstName = person.getFirstName();
    this.lastName = person.getLastName();
    this.gender = person.getGender().toString();
    this.studyArea = "";
  }

  /*
   Getter methods for main properties of an account
   */

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
