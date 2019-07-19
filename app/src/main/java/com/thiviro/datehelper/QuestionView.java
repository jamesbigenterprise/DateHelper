package com.thiviro.datehelper;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.List;

public class QuestionView extends AppCompatActivity implements View.OnClickListener {

  private Account account;
  private TagMaster tagMaster;
  private Question question;
  private List<Comment> comments;
  private TextView questionTextView;
  private EditText commentEditText;
  private ImageButton upVote;
  private ImageButton downVote;
  private Button addCommentButton;
  private ListView commentList;
  private ProgressBar progressBar;
    public static final String SHARED_PREFS = "sharedPrefs";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_question_view);
      progressBar = findViewById(R.id.progressCircle);
      upVote = findViewById(R.id.up_button);
      downVote = findViewById(R.id.down_button);
      questionTextView = findViewById(R.id.question_text_view);
      commentEditText = findViewById(R.id.write_comment_edit);
      addCommentButton = findViewById(R.id.add_comment);

      SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
      Gson gson = new Gson();

      String accountJson;
      String questionJson = getIntent().getDataString();
      accountJson = sharedPreferences.getString(MainActivity.ACCOUNT,"error shared pref");
      account =  gson.fromJson(accountJson, Account.class);
      question = gson.fromJson(questionJson, Question.class);
      commentList = findViewById(R.id.interestList);
      comments = question.getComments();
      questionTextView.setText(question.getQuestion());
      createList();
      markVote();
  }

    private class UpdateTagMasterAsyncTask extends AsyncTask<Void, Integer, Void> {
        private WeakReference<QuestionView> weakReference;
        private TagMaster downloadedTagMaster;
        public UpdateTagMasterAsyncTask (QuestionView context){
            weakReference = new WeakReference<QuestionView>(context);
        }


        @Override
        protected void onPreExecute() {
          super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            upVote.setVisibility(View.INVISIBLE);
            downVote.setVisibility(View.INVISIBLE);
            questionTextView.setVisibility(View.INVISIBLE);
            commentEditText.setVisibility(View.INVISIBLE);
            addCommentButton.setVisibility(View.INVISIBLE);
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected Void doInBackground(Void ...voids) {
          publishProgress(10);
          Gson gson = new Gson();
          TagMaster tagMaster = null;//
          publishProgress(20);
          /**
           * BACKEND
           * //Todo Upload the TagMaster with the changes
           */
          return null;
        }

        @Override
        protected void onPostExecute(Void  v) {
            super.onPostExecute(v);
            markVote();
            upVote.setVisibility(View.VISIBLE);
            downVote.setVisibility(View.VISIBLE);
            questionTextView.setVisibility(View.VISIBLE);
            commentEditText.setVisibility(View.VISIBLE);
            addCommentButton.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }

    }

    private class UploadQuestionAsyncTask extends AsyncTask<Void, Integer, Void>{
      WeakReference<QuestionView>weakReference;

        public UploadQuestionAsyncTask(QuestionView context) {
          weakReference = new WeakReference<QuestionView>(context);
        }

        @Override
      protected void onPreExecute() {
          super.onPreExecute();
          progressBar.setVisibility(View.VISIBLE);
          upVote.setVisibility(View.INVISIBLE);
          downVote.setVisibility(View.INVISIBLE);
          questionTextView.setVisibility(View.INVISIBLE);
          commentEditText.setVisibility(View.INVISIBLE);
          addCommentButton.setVisibility(View.INVISIBLE);
      }

      @Override
      protected Void doInBackground(Void... voids) {
      /**
       * Todo Upload Question
       *
       */
        return null;
      }

      @Override
      protected void onProgressUpdate(Integer... values) {
          super.onProgressUpdate(values);
      }

      @Override
      protected void onPostExecute(Void aVoid) {
          super.onPostExecute(aVoid);
          markVote();
          upVote.setVisibility(View.VISIBLE);
          downVote.setVisibility(View.VISIBLE);
          questionTextView.setVisibility(View.VISIBLE);
          commentEditText.setVisibility(View.VISIBLE);
          addCommentButton.setVisibility(View.VISIBLE);
          progressBar.setVisibility(View.INVISIBLE);
      }
  }

  private class AddCommentAsyncTask extends AsyncTask<String, Void, Question>{
      WeakReference<QuestionView>weakReference;
      Question thisQuestion;
      Account thisaccount;

      public AddCommentAsyncTask(QuestionView context) {
          weakReference = new WeakReference<QuestionView>(context);
          thisQuestion = weakReference.get().question;
          thisaccount = weakReference.get().account;
      }
      @Override
      protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
        upVote.setVisibility(View.INVISIBLE);
        downVote.setVisibility(View.INVISIBLE);
        questionTextView.setVisibility(View.INVISIBLE);
        commentEditText.setVisibility(View.INVISIBLE);
        addCommentButton.setVisibility(View.INVISIBLE);
      }

      @Override
      protected Question doInBackground(String... strings) {
          Comment newComment = new Comment(thisaccount, strings[0], thisaccount.getImageURL());
          thisQuestion.addComment(newComment);
          /**
           * Todo Upload question with the new comment
           */
          return null;
      }

      @Override
      protected void onPostExecute(Question question) {
          super.onPostExecute(question);
          markVote();
          upVote.setVisibility(View.VISIBLE);
          downVote.setVisibility(View.VISIBLE);
          questionTextView.setVisibility(View.VISIBLE);
          commentEditText.setVisibility(View.VISIBLE);
          addCommentButton.setVisibility(View.VISIBLE);
          progressBar.setVisibility(View.INVISIBLE);
      }


  }


  public void markVote(){
    if(question.hasVoted(account)){
        if(question.getVote(account).equals(1)){
            //set the upVote Button to blue
            upVote.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            downVote.setBackgroundColor(getResources().getColor(R.color.colorTopBar));
        }else {
          //set the downvote to blue
            downVote.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            upVote.setBackgroundColor(getResources().getColor(R.color.colorTopBar));
        }
    }
  }
    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.up_button:
            question.upVote(account, tagMaster);
            UpdateTagMasterAsyncTask task1 = new UpdateTagMasterAsyncTask(QuestionView.this);
            task1.execute();
            UploadQuestionAsyncTask task2 = new UploadQuestionAsyncTask(QuestionView.this);
            task2.execute();
            break;
          case R.id.down_button:
            question.downVote(account, tagMaster);
            UpdateTagMasterAsyncTask task1case2 = new UpdateTagMasterAsyncTask(QuestionView.this);
            task1case2.execute();
            UploadQuestionAsyncTask task2case2 = new UploadQuestionAsyncTask(QuestionView.this);
            task2case2.execute();
            break;
          case R.id.add_comment:
            Editable editable = commentEditText.getText();
            String commentString = editable.toString();
            AddCommentAsyncTask task1case3 = new AddCommentAsyncTask(QuestionView.this);
            task1case3.execute(commentString);
            break;
      }
    }

    private void createList(){
      CommentListAdapter listAdapter = new CommentListAdapter(this, R.layout.comment_list, comments);
      commentList.setAdapter(listAdapter);
    }

}
