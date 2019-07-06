package com.thiviro.datehelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Save distinct votes and who voted
 *
 * @author Thiago
 * @version 1
 * @since 1
 */
public class VoteTracker {
  private Map<Account, Integer> maleVotes;
  private Map<Account, Integer> femaleVotes;

  //constant numbers
  private static final Integer UP_VOTE = 1;
  private static final Integer DOWN_VOTE = -1;

  //Constructor

  /**
   * Just initialize the Collections
   */
  public VoteTracker() {
    maleVotes = new HashMap<Account, Integer>();
    femaleVotes = new HashMap<Account, Integer>();
  }

  //Check if has voted

  /**
   * Check if the user has voted
   * @param user the user that will be checked
   * @return true if has voted(Account is on the Collection), false if it has not (Account is not the Collection)
   */
  private boolean hasVoted (Account user) { //the user is the key, check if it is in the list
    if (user.getGender()) {
      return maleVotes.containsKey(user);
    } else {
      return femaleVotes.containsKey(user);
    }
  }


  //get percentage of votes

  /**
   * Compare all the votes and return the percentage of Up Votes
   *
   * Use the person to determine if we will return male or female results
   * If the person is male, return female results and vice versa
   * Create counters for Up and Down votes
   * Loop through the collection and if the vote is 1 (UP_VOTE) increase
   * the upvote counter and -1 (DOWN_VOTE) for the Down vote
   * @param person person to get the gender.  It needs to be a person because we will create just a person
   *               to get help on a date, not an entire account
   * @return a float with the percentage of votes
   */
  public float getPercentageOfUpVotes (Person person) {
    if (person.getGender()) {
      //the person is a man, so we will return the female votes
      int tempFemaleUpVotes = 0;
      int tempFemaleDownVotes = 0;
      //if we find a UP vote, increase the Up vote counter
      for (Map.Entry<Account, Integer> vote: femaleVotes.entrySet()){
        if(vote.getValue() == UP_VOTE){
          tempFemaleUpVotes++;
        }else{
          tempFemaleDownVotes++; //the other possible option is an Down vote, so we will add to this counter
        }
      }
      //do this to avoid division by 0
      if(tempFemaleUpVotes != 0) {
        return (float)tempFemaleUpVotes / (tempFemaleUpVotes + tempFemaleDownVotes);
      }else {
        return 0; //no votes yet
      }

    } else {
      //the person is a woman, so we will return the male votes
      int tempMaleUpVotes = 0;
      int tempMaleDownVotes = 0;
      for (Map.Entry<Account, Integer> vote: maleVotes.entrySet()){
        if(vote.getValue() == UP_VOTE){ //if we find a upvote, increase the upvote counter
          tempMaleUpVotes++;
        }else{
          tempMaleDownVotes++; //the other possible option is an downvote, so we will add to this counter
        }
      }
      if(tempMaleUpVotes != 0) { //do this to avoid division by 0
        return (float)tempMaleUpVotes / (tempMaleUpVotes + tempMaleDownVotes);
      }else {

        return 0; //no votes yet
      }
    }
  }

  /**
   * Add a up vote based on the gender of the account
   *
   * check if it is the first vote and the add it
   * if the account has already voted on the opposite option (Down vote)
   * Use the switch method to erase former down vote and add the new up vote
   * @param user Account to check if it has voted or not
   */
  public void upVote(Account user) {
    if (user.getGender()) { //true is male / false is female
      //before saving the vote, check if the user has not voted yet
      if(!hasVoted(user)){
        //no votes yet, so let's compute a new vote
        maleVotes.put(user, UP_VOTE);
      }else{//user has already voted
        //let us check the vote
        if(maleVotes.get(user).equals(DOWN_VOTE) ){
          //it is a down vote, it will be necessary to switch to up Vote
          switchVote(user);
        }//otherwise no action needed since the vote is already saved
      }
    } else { //Female user
      //before saving the vote, check if the user has not voted yet
      if(!hasVoted(user)){
        //no votes yet, so let's compute a new vote
        femaleVotes.put(user, UP_VOTE);
      }else{//user has already voted
        //let us check the vote
        if(femaleVotes.get(user).equals(DOWN_VOTE) ){
          //it is a down vote, it will be necessary to switch to up Vote
          switchVote(user);
        }//otherwise no action needed since the vote is already saved
      }
    }
  }

  /**
   * Add a down vote based on the gender of the account
   *
   * check if it is the first vote and the add it
   * if the account has already voted on the opposite option (Up vote)
   * Use the switch method to erase former up vote and add the new down vote
   * @param user Account to check if it has voted or not
   */
  void downVote(Account user) {
    if (user.getGender()) { //true is male
      //before saving the vote, check if the user has not voted yet
      if(!hasVoted(user)){
        //no votes yet, so let's compute a new vote
        maleVotes.put(user, DOWN_VOTE);
      }else{//user has already voted
        //let us check the vote
        if(maleVotes.get(user).equals(UP_VOTE) ){
          //it is a UP vote, it will be necessary to switch to down Vote
          switchVote(user);
        }//otherwise no action needed since the vote is already saved
      }
    } else { // false is female
      //before saving the vote, check if the user has not voted yet
      if(!hasVoted(user)){
        //no votes yet, so let's compute a new vote
        femaleVotes.put(user, DOWN_VOTE);
      }else{//user has already voted
        //let us check the vote
        if(femaleVotes.get(user).equals(UP_VOTE) ){
          //it is a up vote, it will be necessary to switch to down Vote
          switchVote(user);
        }//otherwise no action needed since the vote is already saved
      }
    }
  }

  /**
   * Remove the former vote and add the opposite one
   *
   * @param user Use the account to check the current vote
   */
  private void switchVote(Account user) {
    Integer currentVote = null;
    //get the current vote
    if (user.getGender()) {//it is a man
      currentVote = maleVotes.get(user);
      if (currentVote.equals(UP_VOTE) ) {//it is a upvote, switch to downvote
        maleVotes.remove(user);
        downVote(user);
      } else {//it is a downvote, switch to Upvote
        maleVotes.remove(user);
        upVote(user);
      }
    } else {//it is a woman
      currentVote = femaleVotes.get(user);
      if (currentVote.equals(UP_VOTE)) {//it is a upvote, switch to downvote
        femaleVotes.remove(user);
        downVote(user);
      } else {//it is a downvote, switch to Upvote
        femaleVotes.remove(user);
        upVote(user);
      }
    }
  }


}
