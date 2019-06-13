package com.thiviro.datehelper;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TagUnitTest {

  @Test
  public void computedMaleVoteCorrectly() {
    boolean male = true; //Male
    Tag tag = new Tag();

    //Test 1
    //Check if the object was created
    assertNotNull(tag);

    //Test 2
    //Check if the vote was computed correctly
    tag.upVote(male);
    int newVote = tag.getUpVotes(male) + 1;
    tag.upVote(male);
    assertNotEquals( newVote, tag.getUpVotes(male) );

    //Test 3
    //Check if the vote in one gender will impact the other
    int femaleCount = tag.getUpVotes(!male);
    assertEquals(0, femaleCount);
  }
}