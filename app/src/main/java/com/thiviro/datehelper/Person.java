package com.thiviro.datehelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A person will describe the user with the gender, name and the tags
 *
 * @author Thiago
 * @version 1
 * @since 1
 */
public class Person {
  private String firstName;
  private String lastName;
  private boolean gender;
  private List<String> tagKeys;
  //constant strings
  private static final String DATE_FIRST_NAME = "Date";
  private static final String DATE_LAST_NAME = "Profile";

  /**
   * Constructor for a real User
   *
   * It will save the information here and the tags in the tag master
   *
   * @param firstName Saves the first name
   * @param lastName Saves the last name
   * @param gender saves the gender
   * @param tags saves the tags
   * @param tagMaster utilized to put all the tags in one place
   */
  public Person(String firstName, String lastName, boolean gender, List<Tag> tags, TagMaster tagMaster) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    //this tag master is to make sure we are using unique tags
    this.tagKeys = new ArrayList<>();
    for (Tag tag: tags){
      //Tag tagHolder = tagMaster.addTag(tag);//make sure the tag is listed in the tag master before adding to this person
      this.tagKeys.add(tagMaster.addTag(tag));
    }
  }

  /**
   * Constructor for the date, it will not be used in an Account so it does not need a name
   *
   * It will take the account of the user who iss creating it to determine the gender and a list of Tags to describe
   * the date profile
   * @param user user who is creating this date profile
   * @param tags tags to describe the date
   * @param tagMaster place to store the tags
   */
  public Person(Account user, List<Tag> tags, TagMaster tagMaster) {
    this.firstName = DATE_FIRST_NAME;
    this.lastName = DATE_LAST_NAME;
    this.gender = user.getGender(); //always the opposite gender of who is asking
    //this tag master is to make sure we are using unique tags
    this.tagKeys = new ArrayList<>();
    for (Tag tag: tags){
      this.tagKeys.add(tagMaster.addTag(tag));
    }
  }

  public boolean getGender() {
    return gender;
  }
  public String getFullName() {
    return firstName.concat(" " + lastName);
  }

  /**
   * Get a list of keys to get the questions that describe this Person
   * @return list of keys to Tags
   */
  public List<String> getTags() {
    return tagKeys;
  }
}

