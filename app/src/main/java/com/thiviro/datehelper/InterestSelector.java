package com.thiviro.datehelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InterestSelector extends AppCompatActivity implements View.OnClickListener {

  private List<String> interests;
  private ListView interestList;
  private ArrayAdapter<String> listAdapter;
  private Button next;
  private Button addMore;

  public static final String SHARED_PREFS = "sharedPrefs";
  public static final String LIST_TAGS = "listTags";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_interest_selector);
    interests = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.interests)));
    next = findViewById(R.id.interest_next);
    addMore = findViewById(R.id.interest_button_add_more);
    interestList = findViewById(R.id.interestList);
    createList();
    next.setOnClickListener(this);
    addMore.setOnClickListener(this);
  }

  private void createList(){
    listAdapter = new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_multiple_choice, interests);
    interestList.setAdapter(listAdapter);
    interestList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
  }

  private ArrayList<String> getSelection(){
    SparseBooleanArray checked = interestList.getCheckedItemPositions();
    ArrayList<String> selection = new ArrayList<>();
    for (int i = 0;i < checked.size();i++){
      selection.add(listAdapter.getItem(checked.keyAt(i)));
    }

    return selection;
  }

  private void createDialog(){
    LayoutInflater inflater = LayoutInflater.from(this);
    final View mView = inflater.inflate(R.layout.text_input_dialog, null);
    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    dialog.setView(mView);

    dialog.setCancelable(false)
        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {


            final EditText entry = mView.findViewById(R.id.dialog_edit_text);
            if (entry != null){
              interests.add(entry.getText().toString());
              listAdapter.notifyDataSetChanged();
              // TODO add list to shared prefs
            }
            dialogInterface.dismiss();

          }

        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
          }
        });


    AlertDialog alertDialog = dialog.create();
    alertDialog.show();
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()){
      case R.id.interest_next:
        System.out.println(getSelection());
        //The selections were made, so lets turn them into tags
        // turn each string into a Tag
        List<Tag> listofTags = new ArrayList<Tag>();
        for (String tag : interests) {
            listofTags.add(new Tag(tag));
        }
        //Type type = new TypeToken<ArrayList<Tag>>() {}.getType();
        Gson gson = new Gson();
        String json = gson.toJson(listofTags);
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LIST_TAGS, json);
        editor.apply();
        startActivity(new Intent(this, StudyArea.class));

        break;
      case R.id.interest_button_add_more:
        createDialog();
        break;
    }
  }
}
