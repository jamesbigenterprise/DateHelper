package com.thiviro.datehelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class InterestSelector extends AppCompatActivity {

  private ListView interestList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_interest_selector);
    interestList = findViewById(R.id.interestList);
    createList();
  }

  private void createList(){
    String[] options = getResources().getStringArray(R.array.interests);
    ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_multiple_choice, options);
    interestList.setAdapter(listAdapter);
    interestList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

  }
}
