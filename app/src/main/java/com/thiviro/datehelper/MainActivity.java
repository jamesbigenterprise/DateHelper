package com.thiviro.datehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }


  public void openActivity(View view){
    Intent intent = new Intent(this, ProfileSelector.class);
    startActivity(intent);
  }

  public void helpMeOnaDate(View view) {
    Intent intent = new Intent(this, ShowResults.class);
    startActivity(intent);
  }
  public void askaQuestion(View view) {
    Intent intent = new Intent(this, NewQuestion.class);
    startActivity(intent);
  }
}
