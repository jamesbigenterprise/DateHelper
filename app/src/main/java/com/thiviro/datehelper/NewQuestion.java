package com.thiviro.datehelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewQuestion extends AppCompatActivity implements View.OnClickListener {

  //Interests
  private List<String> interests;
  private ListView interestList;
  private ArrayAdapter<String> listAdapter;

  //Suggestions
  private List<String> suggestions;
  private ArrayAdapter<String> suggestionsAdapter;
  private ListView questionSuggestion;

  private List<Tag> listofTags;
  private Button addMore;
  private Button newQuestionButton;
  private CircleImageView profilePhoto;
  private EditText writeQuestion;
  private String question;
  private String summary;
  private Question newQuestion;
  private String image_url;
  private Account account;
  private TagMaster tagMaster;
  private TextView authorTextVew;

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
    //SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);


    interestList = findViewById(R.id.interestList);
    questionSuggestion = findViewById(R.id.question_suggestion);
    createInterestsList(this);
    createSuggestionList(this);

    newQuestionButton = findViewById(R.id.ask_new_question);
    addMore = findViewById(R.id.interest_button_add_more);
    // Set onclick listeners
    newQuestionButton.setOnClickListener(this);
    addMore.setOnClickListener(this);

    // get from Shared Preferences
    new Thread(new Runnable() {
      @Override
      public void run() {
        // UI references

        profilePhoto = findViewById(R.id.profile_photo);

        //authorTextVew =  findViewById(R.id.author_name_textView);
        writeQuestion = findViewById(R.id.write_new_question);
        writeQuestion.addTextChangedListener(watcher);
        writeQuestion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View view, boolean b) {
            if(!b){
              suggestions.clear();
              suggestionsAdapter.notifyDataSetChanged();
            }

          }
        });


        account =  prefHandler.getAccount();
        tagMaster = prefHandler.getTagMaster();
        image_url = prefHandler.getPhotoURL();
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Glide.with(getApplicationContext()).load(image_url).into(profilePhoto);
          }
        });
      }
    }).start();

    try {
      Glide.with(this).load(image_url).into(profilePhoto);
    } catch (Exception e){
      Toast.makeText(getParent(),
          "Image could not be loaded, check connectivity",
          Toast.LENGTH_SHORT).show();
    }

    // item not found:
    //authorTextVew.setText(account.getName());
  }


  private ArrayList<String> getSelection(){
    SparseBooleanArray checked = interestList.getCheckedItemPositions();
    ArrayList<String> selection = new ArrayList<>();
    for (int i = 0;i < checked.size();i++){
      selection.add(listAdapter.getItem(checked.keyAt(i)));
    }
    return selection;
  }

  private void createInterestsList(final Activity activity){
    final WeakReference<Activity> activityRef = new WeakReference<>(activity);
    final Activity mActivity = activityRef.get();
    new Thread(new Runnable() {
      @Override
      public void run() {
        interests = new ArrayList<>(Arrays.asList(getResources()
            .getStringArray(R.array.interests)));
        listAdapter = new ArrayAdapter<>(mActivity,
            android.R.layout.simple_list_item_multiple_choice, interests);
        interestList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        interestList.setAdapter(listAdapter);
      }
    }).start();


  }

  private void createSuggestionList(Activity activity){
    final WeakReference<Activity> activityRef = new WeakReference<>(activity);
    final Activity mActivity = activityRef.get();
    suggestions = new ArrayList<>();
    suggestionsAdapter = new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_1, suggestions);

    new Thread(new Runnable() {
      @Override
      public void run() {
        suggestions = new ArrayList<>();
        suggestionsAdapter = new ArrayAdapter<>(mActivity,
            android.R.layout.simple_list_item_1, suggestions);
        questionSuggestion.setAdapter(suggestionsAdapter);
        questionSuggestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

          }
        });
      }
    }).start();

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
          String questionJson = new Gson().toJson(newQuestion);
          intent.putExtra(NEW_QUESTION_EXTRA, questionJson);
          startActivity(intent);
        }
        break;
      case R.id.interest_button_add_more:
        createDialog();
        break;
      case R.id.question_suggestion:
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
      newQuestion = new Question(account,question,summary,listofTags, tagMaster);
      Log.d(DEBUG_LOG, "Question text == " + newQuestion.getQuestion());
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
    }
    @Override
    public void afterTextChanged(Editable s) {
    }
  };

  public void suggestion() {

    APIWorker api =
        new APINewQuestion(this, APIWorker.ENDPOINT_QUESTIONS, APIWorker.GET);
    api.execute();
    suggestionsAdapter.notifyDataSetChanged();

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
                question_suggestion.setText(question);
              //create intent to the question
              break;
            }
        }
        */
  }

  protected class APINewQuestion extends APIWorker {

    protected APINewQuestion(Activity activity, String endpoint, String method){
      super(activity, endpoint, method);
    }

    @Override
    protected void onPostExecute(String response){
      final Activity activityRef = activity.get();

        Gson gson = new Gson();
        Type questionList = new TypeToken<ArrayList<Question>>() {}.getType();
        final List<Question> questionList1 = gson.fromJson(response, questionList);
        if (questionList1 != null) {
          String JSON = gson.toJson(question);
          suggestions.clear();
          for (Question q : questionList1) {
            suggestions.add(q.getSummary());
          }
        } else {
          Toast.makeText(activityRef
              , "Request was not completed, the device might not be connected",
              Toast.LENGTH_SHORT).show();
        }

        }


//            TextView questionSuggestion = activityRef.findViewById(R.id.questionSuggestion);
//            questionSuggestion.setText(response);
    }
  }


