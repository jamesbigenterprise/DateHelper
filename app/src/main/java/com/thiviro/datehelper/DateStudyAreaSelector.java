package com.thiviro.datehelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Map;

public class DateStudyAreaSelector extends AppCompatActivity implements View.OnClickListener {

    private List<String> areas;
    private ListView studyArea;
    private Button next;
    private Button addMore;
    private String areaSelected;
    private Person profile;
    private Account userAccount;
    ArrayAdapter<String> listAdapter;

    public static final String SHARED_PREFS = "sharedPrefs";
    public final static String HELP_RESULTS = "help_results";
    private final static String LOG_DEBUG = "StudyArea()";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_area);
        areas = new ArrayList<>(Arrays.asList(getResources().
                getStringArray(R.array.study_area)));
        next = findViewById(R.id.study_next);
        addMore = findViewById(R.id.study_button_add_more);
        studyArea = findViewById(R.id.studyArea);
        createList();

        //get the user profile
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String jsonAccount = sharedPreferences.getString(MainActivity.ACCOUNT, "");
        userAccount = gson.fromJson(jsonAccount, Account.class);



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
                Tag studyAreaTag = new Tag(areaSelected);
                Log.d(LOG_DEBUG, "GETTING THE TAGS");
                //add this Tag to the list of Tags
                SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                Gson gson = new Gson();
                String json = sharedPref.getString(DateInterestsSelector.LIST_DATE_TAGS, null);
                Type type = new TypeToken<ArrayList<Tag>>() {}.getType();
                List<Tag> listofTags = new ArrayList<Tag>();
                listofTags = gson.fromJson(json, type);
                listofTags.add(studyAreaTag);
                Log.d(LOG_DEBUG, "ADDED THE NEW TAG");
                //put it back to shared prefs
                SharedPreferences.Editor editor = sharedPref.edit();
                json = gson.toJson(listofTags);
                editor.putString(InterestSelector.LIST_TAGS, json);
                editor.apply();

                TagMaster tagmaster = gson.fromJson(sharedPref.getString(MainActivity.TAG_MASTER, ""), TagMaster.class);

                //create the date profile
                profile =  new Person(userAccount, listofTags,tagmaster);
                next.setOnClickListener(this);
                addMore.setOnClickListener(this);

                //help on date
                String qmjson = sharedPref.getString(MainActivity.QUESTION_MASTER, "");
                QuestionsMaster qm = gson.fromJson(qmjson, QuestionsMaster.class);
                Map<Question, Question> results = qm.helpOnDate(profile);
                String resultsJson = gson.toJson(results);
                editor.putString(HELP_RESULTS, resultsJson);
                editor.apply();

                Log.d(LOG_DEBUG, "SAVED BACK TO SHARED PREF");


                startActivity(new Intent(this, ShowResults.class));
                break;
            case R.id.study_button_add_more:
                createDialog();
                break;
        }
    }


}



