package com.thiviro.datehelper;

public class Tag {
    static final int  INITIAL_VOTES = 0;

  private int maleUpVote;
  private int maleDownVote;
  private int femaleUpVote;
  private int femaleDownVote;

    public Tag() {
        this.maleUpVote = INITIAL_VOTES;
        this.maleDownVote = INITIAL_VOTES;
        this.femaleUpVote = INITIAL_VOTES;
        this.femaleDownVote = INITIAL_VOTES;
    }

    public void upVote (boolean gender) {

  }
  public void DownVote (boolean gender) {

  }

  public int getUpVotes (boolean gender) {
      return 0;
  }
  public int getDownVotes (boolean gender) {
    return 0;
  }

}
