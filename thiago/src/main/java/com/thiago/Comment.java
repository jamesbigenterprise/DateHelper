package com.thiago;

public class Comment {
  private Account author;
  private String comment;

  public Comment(Account author, String comment) {
    this.author = author;
    this.comment = comment;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
