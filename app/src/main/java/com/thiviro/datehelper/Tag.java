package com.thiviro.datehelper;

import com.google.gson.annotations.SerializedName;

import java.util.*;

/**
 * A tag represents a characteristic that can be added to a person and to a Question
 *
 * The description will be a string and it can be voted, storing the vote information in the cote tracker
 * The Tag will keep track or all the questions that it is being used in
 *
 * @author Thiago
 * @version 1
 * @since 1
 */
public class Tag {

  //raw data
  @SerializedName("name")
  private String tagName;
  private VoteTracker voteTracker;
  //usage, track all the questions that use this tag
  private List<String> questionsInUse;

  //constructor

  /**
   * In the constructor you always need to provite the Tag name and it will create an empty list of questions and
   * initialize the vote tracker to receive future votes
   * @param tagName the string that will describe the tag
   */
  public Tag(String tagName) {
    this.tagName = tagName.toLowerCase();
    questionsInUse = new ArrayList<>();
    voteTracker = new VoteTracker();
  }

  //associate question - Add and Remove it

  /**
   * Add a question that uses this tag
   * @param question the question that will use this tag
   */
  void addQuestion(Question question) {
    questionsInUse.add(question.getQuestion());
  }

  /**
   * A question is no longer using this Tag, so this question will be removed
   * @param question the question that is not using this tag anymore
   */
  void removeQuestion(Question question) {
    if (questionsInUse.contains(question.getQuestion())) {
      questionsInUse.remove(question);
    }
  }

  /**
   * Check all the questions that use this Tag and return the question with the highest number of votes
   *
   * Organize the questions by the votes they received in a HashMap using the vote as a key and question as object
   * Put the results in a tree that will automatically organize the results by the keys and return the first,
   * that is the Top Question
   * @param person Person to get the gender for the votes
   * @return
   */
  public Question getTopQuestion(Person person) {
    Map<Float, Question> questionsMap = new HashMap<>();
    List<Question> questionsInUseDownloaded = new ArrayList<>();
    /**
     * BACKEND
     *
     * Download all the questions in use
     * and save in questionsInUseDownloaded
     */
    for (Question question : questionsInUseDownloaded) {
      questionsMap.put(question.getPercentOfUpVotes(person), question);
      }
    TreeMap<Float, Question> treeOfQuestions = new TreeMap<>(questionsMap);
    return treeOfQuestions.get(( treeOfQuestions).firstKey());
  }

  /**
   * Get a the name of the Tag that can be used as a key to retrieve the Tag in the Tag master
   * @return string (summary) that is a key in the tag master
   */
  public String getTagName() {
    return tagName;
  }

  /**
   * Chage the string that describes the tag
   * @param tagName string thar describes the tag
   */
  public void setTagName(String tagName) {
    this.tagName = tagName;
  }

  /**
   * Get the UP Votes compared to the Down votes, based on the gender of the Profile passed.
   * @param user the user who wants to know the votes (get the gender to know wich votes to return)
   * @return Percentage of UP Votes for this particular Tag
   */
  public float getPercentOfUpVotes(Person user) {
    return voteTracker.getPercentageOfUpVotes(user);
  }

  /**
   * Add an Up Vote to thia question
   * @param user take the user to know which gender to add the vote
   */
  void upVote(Account user) {
    voteTracker.upVote(user);
  }

  /**
   * Add an Down Vote to thia question
   * @param user take the user to know which gender to add the vote
   */
  void downVote(Account user) {
    voteTracker.downVote(user);
  }
}