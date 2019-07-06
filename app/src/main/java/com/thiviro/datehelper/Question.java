package com.thiviro.datehelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Store the question and a summary of it in Strings
 *
 * @author Thiago
 * @version 1
 * @since 1
 */
public class Question {
  private Account author;
  private String question;
  private String summary;//The question summarized in an action like: "Go to the movies;"



  //Collections
  public VoteTracker voteTracker;
  private List<String> tagKeys;
  private List<Comment> comments;
  //use this to avoid duplicate questions and have them in a single place
  TagMaster tagMaster;

  //Constructor

  /**
   * Build the Question
   *
   * @param author The account that created the question
   * @param question a string containing the question
   * @param summary a string containing the summary of hte question
   * @param tags A list of Tags describing this question
   * @param tagMaster The way to keep all the tags in one place
   */
  public Question(Account author, String question, String summary, List<Tag> tags, TagMaster tagMaster) {
    this.author = author;
    this.question = question;
    this.summary = summary;
    this.voteTracker = new VoteTracker();
    //this tag master is to make sure we are using unique tags
    this.tagKeys = new ArrayList<>();
    this.tagMaster = tagMaster;
    for (Tag tag: tags){
      //make sure the tag is listed in the tag master before adding to this person
      tag.addQuestion(this);
      this.tagKeys.add(tagMaster.addTag(tag));
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

  /**
   * Get the percentage of up Votes using the vote Tracker
   * @param person Use the person to know the gender
   * @return the percentage from the vote tracker
   */
  public float getPercentOfUpVotes (Person person) {
    return voteTracker.getPercentageOfUpVotes(person);
  }

  /**
   * Up vote the question and all the children Tags
   *
   * Use the vote tracker to up vote
   * get a list of tags using the keys
   * Up vote all the tags
   * @param user account to use in the vote
   */
  void upVote(Account user) {
      voteTracker.upVote(user);
      List<Tag> tags = tagMaster.getTagsPack(tagKeys);
      for (Tag tag : tags) {//apply the same vote to all the children tags
         tag.upVote(user);
      }
  }

  /**
   * Down vote the question and all the children Tags
   *
   * Use the vote tracker to up vote
   * get a list of tags using the keys
   * down vote all the tags
   * @param user account to use in the vote
   */
  void downVote(Account user) {
    voteTracker.downVote(user);
    List<Tag> tags = new ArrayList<>(tagMaster.getTagsPack(tagKeys));
    for (Tag tag : tags) {//apply the same vote to all the children tags
      tag.downVote(user);
    }
  }

  public List<Comment> getComments(){
    return comments;
  }
}
