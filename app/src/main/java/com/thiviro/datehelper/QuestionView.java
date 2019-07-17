package com.thiviro.datehelper;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.gson.Gson;

public class QuestionView extends AppCompatActivity implements View.OnClickListener {

  private Account account;
  private TagMaster tagMaster;
  private Question question;
  private ImageButton upVote;
  private ImageButton downVote;

    public static final String SHARED_PREFS = "sharedPrefs";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_question_view);
      SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
      Gson gson = new Gson();
      String accountJson;
      accountJson = sharedPreferences.getString(MainActivity.ACCOUNT,"error shared pref");
      account =  gson.fromJson(accountJson, Account.class);
      String tmJson = sharedPreferences.getString(MainActivity.TAG_MASTER, "");
      tagMaster = gson.fromJson(tmJson, TagMaster.class);
      upVote = findViewById(R.id.up_button);
      downVote = findViewById(R.id.down_button);
  }

  void upVote(View view) {
      question.upVote(account, tagMaster);
  }

  void downVote (View view) {
    question.downVote(account, tagMaster);
  }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.up_button:
            question.upVote(account, tagMaster);
            break;
          case R.id.down_button:
            question.downVote(account, tagMaster);
            break;
      }
    }
}
