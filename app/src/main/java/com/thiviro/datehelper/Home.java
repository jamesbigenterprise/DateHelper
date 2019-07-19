package com.thiviro.datehelper;


import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * The home activity loads the view of the main screen of the app after login
 *
 * This activity's purpose is to connect with the two main workflows of the app
 * To ask for help on a data and to ask a question
 */
public class Home extends AppCompatActivity {

  public static final String SHARED_PREFS = "sharedPrefs";
  public static final String TAG = "Home";
  private TagMaster tagMaster;
  private QuestionsMaster questionsMaster;
  private SharedPreferences sharedPreferences;
  private ProgressBar progressBar;
  private Button helpOnDate;
  private Button askQuestion;
  Gson gson;

  /**
   * The onCreate method sets the content view
   *
   * @param savedInstanceState Instance saved used to restore the app status when
   *                           a change occurs
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    progressBar = findViewById(R.id.progress_bar);
    helpOnDate = findViewById(R.id.help_date_button);
    askQuestion = findViewById(R.id.ask_question_button);

    sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

    gson = new Gson();
    String tagMasterJson = sharedPreferences.getString(MainActivity.TAG_MASTER, "");
    tagMaster = gson.fromJson(tagMasterJson, TagMaster.class);
    String questionMasterJson = sharedPreferences.getString(MainActivity.QUESTION_MASTER, "");
    questionsMaster = gson.fromJson(questionMasterJson, QuestionsMaster.class);
    CreateTagMasterAsyncTask task = new CreateTagMasterAsyncTask(Home.this);

    //Log.d(TAG, "the changes were made, we have questions == " + questionsMaster.getQuestionsMasterMap().size());
    synchronized (this){
      task.execute(tagMaster);
    }


  }

  void updateSharedP(){
    //Log.d(TAG, "updateSharedP() -> the changes were made, we have questions after async inside synchronized == " + questionsMaster.getQuestionsMasterMap().size());

    SharedPreferences.Editor editor = sharedPreferences.edit();
    String tmJson = gson.toJson(tagMaster);
    String qmJason = gson.toJson(questionsMaster);
    editor.putString(MainActivity.TAG_MASTER, qmJason);
    editor.putString(MainActivity.QUESTION_MASTER, tmJson);
    editor.apply();
    //Log.d(TAG, "updateSharedP() -> this is what we are putting into the shared preferences == " + qmJason);
    System.out.println(TAG +  "updateSharedP() -> this is what we are putting into the shared preferences == " + qmJason);
    //Toast.makeText(Home.this, " updateSharedP() -> AsyncTask Done, updated data is now in shared preferences, number of questions == " + questionsMaster.getQuestionsMasterMap().size() + " And number of Tags == " + tagMaster.getAllTags().size() , Toast.LENGTH_LONG).show();
  }
  private class CreateTagMasterAsyncTask extends AsyncTask<TagMaster, Integer, TagMaster>{
    private WeakReference<Home> weakReference;

    public CreateTagMasterAsyncTask(Home weakReference) {
      this.weakReference = new WeakReference<>(weakReference);
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();

      //progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
      super.onProgressUpdate(values);
      progressBar.setProgress(values[0]);
    }
    @Override
    protected TagMaster doInBackground(TagMaster... tagMasters) {
      Gson gson = new Gson();

      QuestionsMaster questionsMaster = new QuestionsMaster();
      publishProgress((1 * 100) / 10);
      Tag myTag1 = new Tag("Pets");
      Tag myTag2 = new Tag("Dogs");
      Tag myTag3 = new Tag("Cats");
      Tag myTag4 = new Tag("Movies");
      Tag myTag5 = new Tag("Marvel");
      Tag myTag6 = new Tag("Sports");
      Tag myTag7 = new Tag("Bike");
      Tag myTag8 = new Tag("Swim");
      Tag myTag9 = new Tag("Soccer");
      Tag myTag10 = new Tag("Golf");
      Tag myTag11 = new Tag("Human");
      Tag myTag12 = new Tag("Tech");
      Tag myTag13 = new Tag("Outdoor");
      Tag myTag14 = new Tag("Food");
      Tag myTag15 = new Tag("Spiritual");
      Tag myTag16 = new Tag("Library");
      Tag myTag17 = new Tag("BYUI");
      Tag myTag18 = new Tag("Devotional");
      Tag myTag19 = new Tag("Church");
      Tag myTag20 = new Tag("Ice Cream");

      publishProgress(20);
      Log.d(TAG, "Created 20 Tags");
      tagMasters[0].addTag(myTag1);
      tagMasters[0].addTag(myTag2);
      tagMasters[0].addTag(myTag3);
      tagMasters[0].addTag(myTag4);
      tagMasters[0].addTag(myTag5);
      tagMasters[0].addTag(myTag6);
      tagMasters[0].addTag(myTag7);
      tagMasters[0].addTag(myTag8);
      tagMasters[0].addTag(myTag9);
      tagMasters[0].addTag(myTag10);
      tagMasters[0].addTag(myTag11);
      tagMasters[0].addTag(myTag12);
      tagMasters[0].addTag(myTag13);
      tagMasters[0].addTag(myTag14);
      tagMasters[0].addTag(myTag15);
      tagMasters[0].addTag(myTag16);
      tagMasters[0].addTag(myTag17);
      tagMasters[0].addTag(myTag18);
      tagMasters[0].addTag(myTag19);
      tagMasters[0].addTag(myTag20);

      publishProgress(30);
      return tagMasters[0];
    }

    @Override
    protected void onPostExecute(TagMaster s) {
      super.onPostExecute(s);
      Home home = weakReference.get();
      home.tagMaster = s;
      home.questionsMaster = new QuestionsMaster();
      CreateQuestionsMasterAsyncTask task2 = new CreateQuestionsMasterAsyncTask(Home.this);
      task2.execute(home.questionsMaster);
    }

  }

  private class CreateQuestionsMasterAsyncTask extends AsyncTask<QuestionsMaster, Integer, QuestionsMaster>{
    private WeakReference<Home> weakReference;
    private TagMaster tagMaster;
    public CreateQuestionsMasterAsyncTask(Home weakReference) {
      this.weakReference = new WeakReference<>(weakReference);
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      this.tagMaster = weakReference.get().tagMaster;
    }

    @Override
    protected QuestionsMaster doInBackground(QuestionsMaster... questionsMasters) {
      publishProgress(40);

      //Log.d(TAG, "TASK2 doInBackground -> the QM SIZE IN THE BEGINING is == " + questionsMasters[0].getQuestionsMasterMap().size());
      String qmJason = gson.toJson(questionsMasters[0]);
      Log.d(TAG, "TASK2 doInBackground -> the resulting json is == " + qmJason);
      List<Tag> animalTags = new ArrayList<Tag>();
      animalTags.add(tagMaster.getAllTags().get(0));
      animalTags.add(tagMaster.getAllTags().get(1));
      animalTags.add(tagMaster.getAllTags().get(2));
      List <Tag> sportTags = new ArrayList<Tag>();
      sportTags.add(tagMaster.getAllTags().get(3));
      sportTags.add(tagMaster.getAllTags().get(4));
      sportTags.add(tagMaster.getAllTags().get(5));
      sportTags.add(tagMaster.getAllTags().get(6));
      sportTags.add(tagMaster.getAllTags().get(7));
      sportTags.add(tagMaster.getAllTags().get(8));
      sportTags.add(tagMaster.getAllTags().get(9));
      List <Tag> churchTags = new ArrayList<Tag>();
      churchTags.add(tagMaster.getAllTags().get(10));
      churchTags.add(tagMaster.getAllTags().get(11));
      churchTags.add(tagMaster.getAllTags().get(12));
      churchTags.add(tagMaster.getAllTags().get(13));
      churchTags.add(tagMaster.getAllTags().get(14));
      List <Tag> otherTags = new ArrayList<Tag>();
      otherTags.add(tagMaster.getAllTags().get(15));
      otherTags.add(tagMaster.getAllTags().get(16));
      otherTags.add(tagMaster.getAllTags().get(17));
      otherTags.add(tagMaster.getAllTags().get(18));

      Person firstUser = new Person("Thiago", "Alves da Silva", true, churchTags, tagMaster);
      Account firstAccount = new Account(firstUser, "01","noURL");

      publishProgress(50);

      Question myQ1 = new Question(firstAccount,"Should I take her to the movies?", "Go to the movies",otherTags, tagMaster );
      questionsMasters[0].addQuestion(myQ1);
      Log.d(TAG, "TASK2 doInBackground -> Added the first question, now convert to JSON");
      qmJason = gson.toJson(questionsMasters[0]);
      Log.d(TAG, "TASK2 doInBackground -> the resulting json is == " + qmJason);
      Question myQ2 = new Question(firstAccount,"Should I Kiss her on the first date?", "Kiss on the first date",otherTags, tagMaster );
      questionsMasters[0].addQuestion(myQ2);
      Question myQ3 = new Question(firstAccount,"Should I spend any money on the first date?", "Spend money",otherTags, tagMaster );
      questionsMasters[0].addQuestion(myQ3);
      Question myQ4 = new Question(firstAccount,"Do you think it is a good idea to walk her home?", "Walk home",otherTags, tagMaster);
      questionsMasters[0].addQuestion(myQ4);
      Question myQ5 = new Question(firstAccount,"Should we play sports?", "Play Sports",sportTags, tagMaster );
      questionsMasters[0].addQuestion(myQ5);
      Question myQ6 = new Question(firstAccount,"Go for a walk is a good idea?", "Go for a walk",sportTags, tagMaster );
      questionsMasters[0].addQuestion(myQ6);
      Question myQ7 = new Question(firstAccount,"The aquarium is a good idea?", "Go to the aquarium",sportTags, tagMaster );
      questionsMasters[0].addQuestion(myQ7);
      Question myQ8 = new Question(firstAccount,"Walk her pet is a good idea?", "Walk pet", animalTags, tagMaster);
      questionsMasters[0].addQuestion(myQ8);
      Question myQ9 = new Question(firstAccount,"Devotional is a Date?", "Go for devotional",churchTags, tagMaster );
      questionsMasters[0].addQuestion(myQ9);
      Question myQ10 = new Question(firstAccount,"Institute is a Date?", "Go to institute",churchTags , tagMaster);
      questionsMasters[0].addQuestion(myQ10);

      publishProgress(60);
      Log.d(TAG, "Added 10 questions to the Questions master");

      //create a girl to do some voting
      Person secondUser = new Person("Maiane", "Sampaio", false, otherTags, tagMaster);
      Account secondAccount = new Account(secondUser, "02", "nourl");


      publishProgress(70);
      //create a second girl to do some voting
      Person thirdUser = new Person("Tcharla", "Marques", false, otherTags, tagMaster);
      Account thirdAccount = new Account(thirdUser, "03", "noURL");

      publishProgress(80);
      publishProgress(100);

      //Log.d(TAG, "2TASK - this is the question master created == " +  questionsMaster.getQuestionsMasterMap().size());

      return questionsMaster;

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
      super.onProgressUpdate(values);
      progressBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(QuestionsMaster questionsMaster) {
      super.onPostExecute(questionsMaster);
      Home home = weakReference.get();
      home.questionsMaster = questionsMaster;
      home.updateSharedP();

      progressBar.setVisibility(View.INVISIBLE);
      helpOnDate.setVisibility(View.VISIBLE);
      askQuestion.setVisibility(View.VISIBLE);
    }
  }











  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    String login = sharedPreferences.getString(MainActivity.LOGIN, "LOGIN CLIENT ERROR");
    switch(item.getItemId()){
      case R.id.logout:
        switch (login){
          case "FACEBOOK":
            LoginManager.getInstance().logOut();
            break;
        case "GOOGLE":
          googleSignOut();
            break;
        }

        startActivity(new Intent(this, MainActivity.class));
        break;


      default:
        break;
    }
      return true;
  }

  private void googleSignOut(){
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestProfile()
        .build();
    GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    mGoogleSignInClient.signOut()
        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
          }
        });

  }

  /**
   * The helpMeOnaDate method launches the activity
   * to request for help on a date. It calls the ShowResults class.
   *
   * @param view view clicked used to call the next activity
   */
  public void helpMeOnaDate(View view) {


    //===== ADDED THIS SECTION TO TEST COMMUNICATING WITH THE BACK END! DONE!
    APIQuestionWorker test = new APIQuestionWorker(this);
    Thread thread = new Thread(test, "Test");
    thread.start();
    //========================================================================
    //Intent intent = new Intent(this, ShowResults.class);
    //startActivity(intent);
  }

  /**
   * The askaQuestion method launches the activity
   * to start asking questions. It calls the NewQuestion class
   *
   * @param view view clicked used to call the next activity
   */
  public void askaQuestion(View view) {


    Intent intent = new Intent(this, NewQuestion.class);
    startActivity(intent);
  }
}
