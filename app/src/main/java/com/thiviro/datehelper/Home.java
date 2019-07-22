package com.thiviro.datehelper;


import android.app.Activity;
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

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import org.json.JSONException;
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
  private Account account;
  private TagMaster tagMaster;
  private ProgressBar progressBar;
  private Button helpOnDate;
  private Button askQuestion;
  private PreferenceHandler prefHandler;
  private GetAPIWorker getAPIWorker;
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

    prefHandler = new PreferenceHandler(this);
    progressBar = findViewById(R.id.progress_bar);
    helpOnDate = findViewById(R.id.help_date_button);
    askQuestion = findViewById(R.id.ask_question_button);
    account = prefHandler.getAccount();
    tagMaster = prefHandler.getTagMaster();
    getAPIWorker = new GetAPIWorker(this, APIWorker.ENDPOINT_USERS, APIWorker.GET, prefHandler);
    getAPIWorker.execute();

  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    String login = prefHandler.getLogin();
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
        prefHandler.deleteAll();
        startActivity(new Intent(this, MainActivity.class));
        break;
      case R.id.menu_interests:
        startActivity(new Intent(this, InterestSelector.class));
        break;
      case R.id.menu_areas:
        startActivity(new Intent(this, StudyArea.class));
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
//    APIWorker test = new APIWorker(this);
//    Thread thread = new Thread(test, "Test");
//    thread.start();
    //========================================================================
    Intent intent = new Intent(this, DateInterestsSelector.class);
   startActivity(intent);
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


  public void visitProfile(View view) throws JSONException {

    startActivity(new Intent(this, ProfileViewer.class));
  }



  public class UserPutAPIWorker extends APIWorker{

    public UserPutAPIWorker(Activity activity, String endpoint, String method, Object object){
      super(activity, endpoint, method, object);
    }

    @Override
    public void onPostExecute(String response) throws NullPointerException {
      try{
        System.out.println("Response:" + response + " Length: "+response.length());
      } catch (NullPointerException e){
        System.out.println(e.getMessage());
      }

    }

  }
}
