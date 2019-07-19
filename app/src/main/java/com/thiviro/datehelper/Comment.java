package com.thiviro.datehelper;

public class Comment {
  private String author;
  private String comment;
  private String imageURl;

  public Comment(Account author, String comment, String imageURl) {
    this.author = author.getId();
    this.comment = comment;
    this.imageURl = imageURl;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getImageURl() {
    return imageURl;
  }
}
