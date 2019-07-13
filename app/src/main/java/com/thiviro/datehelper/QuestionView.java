package com.thiviro.datehelper;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.gson.Gson;

public class QuestionView extends AppCompatActivity implements View.OnClickListener {

  private Account account;
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
      upVote = findViewById(R.id.up_button);
      downVote = findViewById(R.id.down_button);
  }

  void upVote(View view) {
      question.upVote(account);
  }

  void downVote (View view) {
    question.downVote(account);
  }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.up_button:
            question.upVote(account);
            break;
          case R.id.down_button:
            question.downVote(account);
            break;
      }
    }
}
