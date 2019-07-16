package com.thiviro.datehelper;


import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

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
  private TagMaster tagMaster;
  private QuestionsMaster questionsMaster;
  private SharedPreferences sharedPref;

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

    TestData testData = new TestData(Home.this);
    Thread thread = new Thread(testData);
    thread.start();

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    String login = sharedPref.getString(MainActivity.LOGIN, "LOGIN CLIENT ERROR");
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
