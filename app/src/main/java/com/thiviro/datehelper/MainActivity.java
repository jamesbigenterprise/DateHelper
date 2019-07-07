package com.thiviro.datehelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  public static final String SHARED_PREFS = "sharedPrefs";
  public static final String FIRST_NAME = "first_name";
  public static final String LAST_NAME = "last_name";
  public static final String TAG_MASTER = "tag_master";
  public static final String QUESTION_MASTER = "question_master";
  public static final String ACCOUNT = "account";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_other);

    Button facebookLogin = findViewById(R.id.button_facebook);
    Button googleLogin = findViewById(R.id.button_google);

    facebookLogin.setOnClickListener(this);
    googleLogin.setOnClickListener(this);


  }

  @Override
  public void onClick(View view) {
    switch (view.getId()){
      case R.id.button_facebook:
        //TODO login using facebook
        System.out.println("FACEBOOK LOGIN");
        String firstName = "First Name from Facebook";
        String lastName = "Last Name from Facebook";
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FIRST_NAME, firstName);
        editor.putString(LAST_NAME, lastName);

        //get the tag and questions master from the server
        TagMaster tagmaster = new TagMaster();
        Gson gson = new Gson();
        String json = gson.toJson(tagmaster);
        editor.putString(TAG_MASTER, json);

        QuestionsMaster questionsMaster = new QuestionsMaster(tagmaster);
        String qmJson = gson.toJson(questionsMaster);
        editor.putString(QUESTION_MASTER, qmJson);

        editor.apply();
        startActivity(new Intent(this, ProfileSelector.class));
        break;
      case R.id.button_google:
        //TODO login using google
        System.out.println("GOOGLE LOGIN");
        startActivity(new Intent(this, ProfileSelector.class));
        break;
    }
  }

}
