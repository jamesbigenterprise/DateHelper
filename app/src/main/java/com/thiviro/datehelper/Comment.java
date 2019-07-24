package com.thiviro.datehelper;

/**
 * The comment class represents the main properties that will
 * be part of a user comment
 *
 */
public class Comment {
  private String author;
  private String comment;
  private String imageURl;

  /**
   * Comment non-default constructor
   *
   * @param author      author of the comment
   * @param comment     comment text
   * @param imageURl    URL of the image
   */
  public Comment(Account author, String comment, String imageURl) {
    this.author = author.getId();
    this.comment = comment;
    this.imageURl = imageURl;
  }


  // Getters

  public String getComment() {
    return comment;
  }

  public String getImageURl() {
    return imageURl;
  }

  //Setter

  public void setComment(String comment) {
    this.comment = comment;

  }


}
