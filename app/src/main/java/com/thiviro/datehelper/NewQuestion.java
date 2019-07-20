package com.thiviro.datehelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewQuestion extends AppCompatActivity implements View.OnClickListener {

    private List<String> interests;
    private ListView interestList;
    private List<Tag> listofTags;
    private ArrayAdapter<String> listAdapter;
    private Button addMore;
    private Button newQuestion;
    private CircleImageView profilePhoto;
    private EditText writeQuestion;
    private String question;
    private String summary;
    private Account account;
    private TagMaster tagMaster;
    private QuestionsMaster questionsMaster;
    private TextView authorTextVew;
    private TextView question_sugestion;
    private PreferenceHandler prefHandler;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String NEW_QUESTION_TOAST = "new_question_toast";
    public static final String NEW_QUESTION_EXTRA = "new_question_extra";
    public static final String DEBUG_LOG = "NewQuestion()";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);
        prefHandler = new PreferenceHandler(this);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();

        interests = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.interests)));
        newQuestion = findViewById(R.id.ask_new_question);
        addMore = findViewById(R.id.interest_button_add_more);
        interestList = findViewById(R.id.interestList);
        profilePhoto = findViewById(R.id.profile_photo);
        question_sugestion = findViewById(R.id.question_sugestion);
        authorTextVew =  findViewById(R.id.author_name_textView);
        writeQuestion = findViewById(R.id.write_new_question);
        writeQuestion.addTextChangedListener(watcher);
        createList();
        newQuestion.setOnClickListener(this);
        addMore.setOnClickListener(this);


        String login = prefHandler.getLogin();
        account =  prefHandler.getAccount();
        tagMaster = prefHandler.getTagMaster();
        questionsMaster = prefHandler.getQuestionMaster();


        String image_url = prefHandler.getPhotoURL();

        Glide.with(this).load(image_url).into(profilePhoto);
        authorTextVew.setText(account.getName());
    }


    private ArrayList<String> getSelection(){
        SparseBooleanArray checked = interestList.getCheckedItemPositions();
        ArrayList<String> selection = new ArrayList<>();
        for (int i = 0;i < checked.size();i++){
            selection.add(listAdapter.getItem(checked.keyAt(i)));
        }
        return selection;
    }

    private void createList(){
        listAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, interests);
        interestList.setAdapter(listAdapter);
        interestList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
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
            case R.id.ask_new_question:
                System.out.println(getSelection());
                //The selections were made, so lets turn them into tags
                // turn each string into a Tag
                listofTags = new ArrayList<Tag>();
                for (String tag : interests) {
                    listofTags.add(new Tag(tag));
                }
                boolean savedQuestion = askNewQuestion();
                if(savedQuestion) {
                    Intent intent = new Intent(this, QuestionView.class);
                    intent.putExtra(NEW_QUESTION_EXTRA, summary);
                    startActivity(intent);
                }
                break;
            case R.id.interest_button_add_more:
                createDialog();
                break;
            case R.id.question_sugestion:
                break;
        }
    }

    public boolean askNewQuestion () {
        EditText writeQuestion = findViewById(R.id.write_new_question);
        EditText writeSummary = findViewById(R.id.write_comment_edit);
        question = writeQuestion.getText().toString();
        summary = writeSummary.getText().toString();

        if (question.isEmpty() || summary.isEmpty() || listofTags.isEmpty()) {
            Toast.makeText(NewQuestion.this, "Please write the question, the Summary and select at Least one Tag", Toast.LENGTH_LONG).show();
            return false;
        }else {
            Question newQuestion = new Question(account,question,summary,listofTags, tagMaster);
            questionsMaster.addQuestion(newQuestion);

            /**
             * BACKEND
             *
             * Save the questions Master in the server
             */
            return true;
        }
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            suggestion();
            Toast.makeText(NewQuestion.this, NEW_QUESTION_TOAST + "onTextChanged()", Toast.LENGTH_LONG).show();
            Log.d(DEBUG_LOG, NEW_QUESTION_TOAST + "onTextChanged()");
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public void suggestion() {
        /*
        Log.d(DEBUG_LOG, NEW_QUESTION_TOAST + "suggestion() -  Starting");
        EditText writeQuestion = findViewById(R.id.write_new_question);
        String currentText = writeQuestion.getText().toString();
        Log.d(DEBUG_LOG, NEW_QUESTION_TOAST + "suggestion() -  Retrieved the current text that is == " + currentText);
        Log.d(DEBUG_LOG, NEW_QUESTION_TOAST + "suggestion() - Number of questions saved == " + questionsMaster.getQuestionsMasterMap().size());
        for (Map.Entry<String, Question> entry : questionsMaster.getQuestionsMasterMap().entrySet()){
            String question = entry.getValue().getQuestion();
            Log.d(DEBUG_LOG, NEW_QUESTION_TOAST + "suggestion() -  Loop comparing this question ==  " + question +" With the text == " + currentText);
            boolean hasQuestion = question.contains(currentText);
            if(hasQuestion) {
              //set the text view
                question_sugestion.setText(question);
              //create intent to the question
              break;
            }
        }
        */
    }
}
