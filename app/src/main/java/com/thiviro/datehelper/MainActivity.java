package com.thiviro.datehelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * The MainActivity class as the name implies is the main entry point for the application
 * it implements the OnClickListener interface to provide different behavior
 * to the interactive view components
 *
 * @author Rolando, Thiago, Vitalii
 * @version 2.0
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginButton loginButton;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;
    private Button signOutButton;
    private userGetAPIWorker userGetAPIWorker;


    private CallbackManager callbackManager;

    public static final int RC_SIGN_IN= 9000;
    public static final String TAG = "com.thiviro.datehelper";
    private PreferenceHandler prefHandler;

    private AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                signInButton.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "User Logged Out", Toast.LENGTH_LONG).show();
            } else {
                loadAccount(currentAccessToken);
            }
        }
    };

    /**
     * The onCreate method sets the content view and creates references
     * to the objects in the screen for later interaction. The objects
     * onclick listeners a defined to be this same class.
     *
     * @param savedInstanceState Instance saved used to restore the app status when
     *                           a change occurs
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_other);
        prefHandler = new PreferenceHandler(this);
        //===========Google Sign In Session initializing==============
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton = findViewById(R.id.google_sign_in);
        signOutButton = findViewById(R.id.google_sign_out);
        signInButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);
        //===========================================================


        loginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.setPermissions(Arrays.asList("email", "public_profile"));
        checkLoginStatus();



        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                signInButton.setVisibility(View.GONE);

            }
            @Override
            public void onCancel() {
            }
            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    if (account != null){
      startActivity(new Intent(MainActivity.this, ProfileSelector.class));
    }

  }

    /**
     * after
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    /**
     * After receiving the access token from the login button use it to retrieve
     * information from facebook and the server
     *
     * If the account already exists, find it on the server using the id, download it and
     * save on shared preferences, then go to the home page
     * Otherwise, save the name and id in shared preferences and start the new account workflow
     * @param newAccessToken toke to access facebook information
     */
    private void loadAccount(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String firstName = object.getString("first_name");
                    String lastName = object.getString("last_name");
                    String id = object.getString("id");

                    if (false /*the account already exists*/){
                        startActivity(new Intent(MainActivity.this, ProfileSelector.class));
                    }else {

                        prefHandler.setFirstName(firstName);
                        prefHandler.setLastName(lastName);
                        prefHandler.setID(id);
                        prefHandler.setPhotoURL("https://graph.facebook.com/" + id + "/picture?type=normal");
                        Person person = new Person(prefHandler.getFirstName(), prefHandler.getLastName(),
                            prefHandler.getGender(), new ArrayList<Tag>(), new TagMaster());
                        prefHandler.setAccount(new Account(person, prefHandler.getId(), prefHandler.getPhotoURL()));
                        prefHandler.setLogin("FACEBOOK");

                        Profile profile = Profile.getCurrentProfile();
                        if (profile != null){
                            userGetAPIWorker =
                                new userGetAPIWorker(getParent(), APIWorker.ENDPOINT_USERS +
                                    "/" + profile.getId(), APIWorker.GET);
                            userGetAPIWorker.execute();
                        }

                        //get the tag and questions master from the server
                        TagMaster tagmaster = new TagMaster();
                        tagmaster.addTag(new Tag("Adventurous"));
                        prefHandler.setTagMaster(tagmaster);

                        QuestionsMaster questionsMaster = new QuestionsMaster();
                        prefHandler.setQuestionMaster(questionsMaster);
                        startActivity(new Intent(MainActivity.this, ProfileSelector.class));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //Now that we have the token let us use it to get the actual user info
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name, last_name, email, id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    /**
     * The onclick method includes the actions to be
     * executed when the items are clicked. This method
     * executes the code that allows logging to the application
     * through Facebook or Google.
     *
     * Details about the login are saved to shared preferences
     * to consult later. The tags from the user are added as JSON
     * to be retrieved in other activities.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.google_sign_in:
                googleSignIn();
                break;
            case R.id.google_sign_out:
                googleSignOut();
                break;
        }
    }

    private void googleSignIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void googleSignOut(){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        signInButton.setVisibility(View.VISIBLE);
                        loginButton.setVisibility(View.VISIBLE);
                        signOutButton.setVisibility(View.GONE);
                    }
                });
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.


            prefHandler.setFirstName(account.getGivenName());
            prefHandler.setLastName(account.getFamilyName());
            prefHandler.setID(account.getId());
            prefHandler.setPhotoURL(account.getPhotoUrl().toString());
            Person person = new Person(prefHandler.getFirstName(), prefHandler.getLastName(),
                prefHandler.getGender(), new ArrayList<Tag>(), new TagMaster());
            prefHandler.setAccount(new Account(person, account.getId(), account.getPhotoUrl().toString()));
            prefHandler.setLogin("GOOGLE");
            userGetAPIWorker =
                new userGetAPIWorker(this,
                    APIWorker.ENDPOINT_USERS + "/"+account.getId(), APIWorker.GET);
            userGetAPIWorker.execute();
            startActivity(new Intent(MainActivity.this, ProfileSelector.class));
            signInButton.setVisibility(View.GONE);
            loginButton.setVisibility(View.GONE);
            signOutButton.setVisibility(View.VISIBLE);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            Toast.makeText(this, "Google login failed", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * If the user is already logged in we can go ahead
     * and load the information.
     */
    private void checkLoginStatus() {
        if (AccessToken.getCurrentAccessToken() != null) {
            loadAccount(AccessToken.getCurrentAccessToken());
        }
    }

    private class userGetAPIWorker extends APIWorker{

        protected userGetAPIWorker(Activity activity, String endpoint, String method){
            super(activity, endpoint, method);
        }

        @Override
        protected void onPostExecute(String response) {
            if (response.equals("")){
                Gender gender = prefHandler.getGender();
                String photoURL = prefHandler.getPhotoURL();
                Person mPerson =
                    new Person(prefHandler.getFirstName(), prefHandler.getLastName(),
                        prefHandler.getGender(), new ArrayList<Tag>(), new TagMaster());
                Account mAccount = new Account(mPerson, prefHandler.getId(), photoURL);
                mAccount.setStudyArea(prefHandler.getStudyArea());
                userPostAPIWorker userPostAPIWorker =
                    new userPostAPIWorker(getParent(), APIWorker.ENDPOINT_USERS, APIWorker.POST, mAccount);
                userPostAPIWorker.execute();
            }


        }
    }

    private class userPostAPIWorker extends APIWorker{

        protected userPostAPIWorker(Activity activity, String endpoint, String method, Object object){
            super(activity, endpoint, method, object);
        }

        @Override
        protected void onPostExecute(String response) throws NullPointerException {
           try{
               System.out.println("Response:" + response + " Length: "+response.length());
           } catch (NullPointerException e){
               System.out.println(e.getMessage());
           }

        }

    }

}