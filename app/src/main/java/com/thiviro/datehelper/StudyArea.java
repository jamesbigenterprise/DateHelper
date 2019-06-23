package com.thiviro.datehelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudyArea extends AppCompatActivity implements View.OnClickListener {

  private List<String> areas;
  private ListView studyArea;
  private Button next;
  private Button addMore;
  private String areaSelected;
  ArrayAdapter<String> listAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_study_area);
    areas = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.study_area)));
    next = findViewById(R.id.study_next);
    addMore = findViewById(R.id.study_button_add_more);
    studyArea = findViewById(R.id.studyArea);
    createList();

    next.setOnClickListener(this);
    addMore.setOnClickListener(this);

  }

  private void createList(){

    listAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_single_choice, areas);
    studyArea.setAdapter(listAdapter);
    studyArea.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    studyArea.setItemChecked(0, true);
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
              areas.add(entry.getText().toString());
              listAdapter.notifyDataSetChanged();
              // TODO add list to sharefprefs
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
    switch (view.getId()) {
      case R.id.study_next:
        areaSelected = listAdapter.getItem(studyArea.getCheckedItemPosition());
        System.out.println("Selection: " + areaSelected);
        startActivity(new Intent(this, ProfilePicture.class));
        break;
      case R.id.study_button_add_more:
        createDialog();
        break;
    }
  }
}
