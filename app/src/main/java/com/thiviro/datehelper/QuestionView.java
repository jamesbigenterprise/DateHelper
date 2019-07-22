package com.thiviro.datehelper;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionView extends AppCompatActivity implements View.OnClickListener {

  private Account account;
  private TagMaster tagMaster;
  private Question question;
  private List<Comment> comments;
  private TextView questionTextView;
  private TextView author_name_textView;
  private EditText commentEditText;
  private ImageButton upVote;
  private ImageButton downVote;
  private Button addCommentButton;
  private ListView commentList;
  private ProgressBar progressBar;
  private CircleImageView profilePictureView;
  private PreferenceHandler prefHandler;
  public static final String SHARED_PREFS = "sharedPrefs";
  public static final String DEBUG_LOG = "QuestionView()";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_question_view);

      prefHandler = new PreferenceHandler(this);
      progressBar = findViewById(R.id.progressCircle);
      upVote = findViewById(R.id.up_button);
      downVote = findViewById(R.id.down_button);
      questionTextView = findViewById(R.id.question_text_view);
      commentEditText = findViewById(R.id.write_comment_edit);
      addCommentButton = findViewById(R.id.add_comment);
      author_name_textView =  findViewById(R.id.name_text_vew);
      profilePictureView = findViewById(R.id.profile_photo);

      //DownloadTagMasterAsyncTask task1 = new DownloadTagMasterAsyncTask(this);
      //task1.execute();

      Gson gson = new Gson();

      //String accountJson;
      String questionJson = getIntent().getStringExtra(NewQuestion.NEW_QUESTION_EXTRA);
      account =  prefHandler.getAccount();
      question = gson.fromJson(questionJson, Question.class);
      Glide.with(this).load(account.getImageURL()).into(profilePictureView);

    questionTextView.setText(question.getQuestion());
    author_name_textView.setText(account.getName());
    markVote();

      commentList = findViewById(R.id.interestList);
      if(question.getComments().size() == 0){
        this.comments = new ArrayList<Comment>();
      }else{
        this.comments = question.getComments();
        createList();
      }


  }

    private class UploadTagMasterAsyncTask extends AsyncTask<TagMaster, Integer, Void> {
        private WeakReference<QuestionView> weakReference;
        public UploadTagMasterAsyncTask(QuestionView context){
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
        protected Void doInBackground(TagMaster ...tagMasters) {
          Gson gson = new Gson();
          //tagMasters[0]
          //this one will be uploaded

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

    private class DownloadTagMasterAsyncTask extends AsyncTask<Void, Integer, Void> {
        private WeakReference<QuestionView> weakReference;
        private TagMaster downloadedTagMaster;
        public DownloadTagMasterAsyncTask(QuestionView context){
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
        protected Void doInBackground(Void ...voids) {
            Gson gson = new Gson();
            TagMaster tagMaster = null; //the tagmaster that will be downloaded
            /**
             * BACKEND
             * //Todo Download the TagMaster with the changes
             */
            downloadedTagMaster = tagMaster;
            return null;
        }

        @Override
        protected void onPostExecute(Void  v) {
            super.onPostExecute(v);
            markVote();
            tagMaster = downloadedTagMaster; //set the new tagmaster
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
            UploadTagMasterAsyncTask task1 = new UploadTagMasterAsyncTask(QuestionView.this);
            task1.execute();
            UploadQuestionAsyncTask task2 = new UploadQuestionAsyncTask(QuestionView.this);
            task2.execute();
            break;
          case R.id.down_button:
            question.downVote(account, tagMaster);
            UploadTagMasterAsyncTask task1case2 = new UploadTagMasterAsyncTask(QuestionView.this);
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