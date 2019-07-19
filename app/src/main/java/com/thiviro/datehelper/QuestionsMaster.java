package com.thiviro.datehelper;

import java.util.*;

/**
 * Store all the existing questions and the logic to manipulate them
 *
 * @author Thiago
 * @version 1
 * @since 1
 */
public class QuestionsMaster {

  private static final int MAX_DISPLAY_RESULTS = 3;


  //constructor

  /**
   * Initialize the Map and the reference to the tag master

   */
  public  QuestionsMaster () {


  }

  //add Tag
  //check if the is already on the list

  /**
   * Add a new question
   *
   * if it already exists does just return its key
   * if it does not, add and return its key
   * @param question question to be added
   * @return key to retrieve this question later
   */
  public String addQuestion (Question question) {
    /**
     * Try to download the ques
     */
    /*
    if(questionsMasterMap.containsValue(question)){
      return question.getSummary();
    }else {
      questionsMasterMap.put(question.getSummary(), question);
      return question.getSummary();
    }
    */
    return null;
  }

  //Help on a date

  //It can return null if it find no results

  /**
   * Determine the best questions based on the tags of the profile of the date
   *
   * Gathers the Tags that describe the date profile using the list of keys
   * (in the date profile) Sort the tags retrieved by the number of votes they
   * have received (Use a Tree to sort by the key, the key is a vote)
   * Get the top question of each tag and return a Map with the result questions
   *
   * difference: if you have tags with the same amount of votes, they would be overridden in a Map
   * so we add this difference
   *
   * @param dateProfile a simple profile with tags that describe your date
   * @return A map containing the top questions
   */
  public List <Question>  helpOnDate(Person dateProfile, TagMaster tagMaster) {
    //get the date tags
    List<String> dateKeys = dateProfile.getTags();
    List<Tag> dateTags = new ArrayList<>(tagMaster.getTagsPack(dateKeys));
    Map<Double, Tag> tagsToSort = new HashMap<>();

    //there is a big possibility of repetitive keys(tags with the same amount of votes)
    // so I will add 0.001 to differentiate
    double difference = 0.001;
    for (Tag tag: dateTags) {

      if(tagsToSort.containsKey((double)tag.getPercentOfUpVotes(dateProfile))){
        tagsToSort.put(tag.getPercentOfUpVotes(dateProfile) + difference, tag);
        difference += difference;
        continue;
      }
      tagsToSort.put((double) tag.getPercentOfUpVotes(dateProfile), tag);
    }

    // create a Tree that will automatically sort the objects
    Map <Double, Tag> treeMap = new TreeMap<>(tagsToSort);

    //Get at most 3 Tags
    List<Tag> topTags = new ArrayList<>();
    if (treeMap.size() != 0){
      int i = 0;
      for (Map.Entry<Double, Tag> entry : treeMap.entrySet()){
        if(i > 3) {
          break;
        }
        topTags.add(entry.getValue());
        i++;
      }
    }
    //get the top question of each tag
    Map<Question, Question> topQuestions = new HashMap<Question, Question>(); //use a map because if we get repetitive Questions they will be overridden
    for (Tag tag : topTags) {
      topQuestions.put(tag.getTopQuestion(dateProfile), tag.getTopQuestion(dateProfile));
    }
    ArrayList<Question> resultsList = new ArrayList<Question>();
    for (Map.Entry<Question, Question> entry: topQuestions.entrySet()){
      resultsList.add(entry.getValue());
    }
    return resultsList;
  }
}
