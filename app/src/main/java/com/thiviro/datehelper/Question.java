package com.thiviro.datehelper;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Store the question and a summary of it in Strings
 *
 * @author Thiago
 * @version 1
 * @since 1
 */
public class Question {
  @SerializedName("id")
  private String id;
  @SerializedName("accountId")
  private String author;
  private String question;
  private String summary;//The question summarized in an action like: "Go to the movies;"
  private int upVotes;
  private int downVotes;



  //Collections
  //public VoteTracker voteTracker;
  private List<String> tagsID;
  private List<String> commentsID;
  //private List<Comment> commentsID;
  //use this to avoid duplicate questions and have them in a single place


  //Constructor

  /**
   * Build the Question
   *
   * @param author The ID of the account that created the question
   * @param question a string containing the question
   * @param summary a string containing the summary of hte question
   * @param tags A list of Tags describing this question
   * @param tagMaster The way to keep all the tags in one place
   */
  public Question(Account author, String question, String summary, List<Tag> tags, TagMaster tagMaster) {
    this.id = UUID.randomUUID().toString();
    this.author = author.getId();
    this.question = question;
    this.summary = summary;
    //this.voteTracker = new VoteTracker();
    this.upVotes = 0;
    this.downVotes = 0;
    //this tag master is to make sure we are using unique tags
    this.tagsID = new ArrayList<>();
    this.commentsID = new ArrayList();

    for (Tag tag: tags){
      //make sure the tag is listed in the tag master before adding to this person
      tag.addQuestion(this);
      this.tagsID.add(tagMaster.addTag(tag));
    }
  }

  /**
   * Get a key to retrieve the question in the Questions master
   * @return string (summary) that is a key in the question master
   */
  public String getQuestion() {
    return question;
  }

  /**
   * Get the summary
   * @return summary a short description of the question / an action
   */
  public String getSummary() {
    return summary;
  }

//  /**
//   * Get the percentage of up Votes using the vote Tracker
//   * @param person Use the person to know the gender
//   * @return the percentage from the vote tracker
//   */
//  public float getPercentOfUpVotes (Person person) {
//    return voteTracker.getPercentageOfUpVotes(person);
//  }

//  /**
//   * Up vote the question and all the children Tags
//   *
//   * Use the vote tracker to up vote
//   * get a list of tags using the keys
//   * Up vote all the tags
//   * @param user account to use in the vote
//   */
//  void upVote(Account user, TagMaster tagMaster) {
//      voteTracker.upVote(user);
//      List<Tag> tags = tagMaster.getTagsPack(tagsID);
//      for (Tag tag : tags) {//apply the same vote to all the children tags
//         tag.upVote(user);
//      }
//  }

//  /**
//   * Down vote the question and all the children Tags
//   *
//   * Use the vote tracker to up vote
//   * get a list of tags using the keys
//   * down vote all the tags
//   * @param user account to use in the vote
//   */
//  void downVote(Account user, TagMaster tagMaster) {
//    voteTracker.downVote(user);
//    List<Tag> tags = new ArrayList<>(tagMaster.getTagsPack(tagsID));
//    for (Tag tag : tags) {//apply the same vote to all the children tags
//      tag.downVote(user);
//    }
//  }

//  public List<Comment> getCommentsID(){
//      return commentsID;
//  }

    public List<String> getCommentsID(){
      return commentsID;
  }

  /**
   * Check if the user has voted
   * @param user the user that will be checked
   * @return true if has voted(Account is on the Collection), false if it has not (Account is not the Collection)
   */
//  public boolean hasVoted(Account user) {
//    return voteTracker.hasVoted(user);
//  }

  /**
   * Return a Integer to show the vote
   * @param user the user that will be checked
   * @return 1 if has upVoted, false if it has downVoted
   */
//  public Integer getVote(Account user) {
//    return voteTracker.getVote(user);
//  }

  /**
   * Add new comment to the question
   * @param newComment the comment to be added
   */
//  public void addComment(Comment newComment){
//    commentsID.add(newComment);
//  }

  public void addComment(String newComment){
    commentsID.add(newComment);
  }

  /**
   * Get the ID generated on the Backend
   * @return the ID
   */
  public String getId(){
    return id;
  }
}

