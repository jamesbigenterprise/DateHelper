package com.thiviro.datehelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * The MainActivity class as the name implies is the main entry point for the application
 * it implements the OnClickListener interface to provide different behavior
 * to the interactive view components
 *
 * @author Rolando, Thiago, Vitalii
 * @version 2.0
 */
public class MainActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CircleImageView circleImage;
    private TextView nameDisplay;

    private CallbackManager callbackManager;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String ID = "id";
    public static final String TAG_MASTER = "tag_master";
    public static final String QUESTION_MASTER = "question_master";
    public static final String ACCOUNT = "account";

    private AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
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

        loginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.setPermissions(Arrays.asList("email", "public_profile"));
        checkLoginStatus();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }
            @Override
            public void onCancel() {
            }
            @Override
            public void onError(FacebookException error) {
            }
        });
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

                    //put everything in the view

                    //String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                    //RequestOptions requestOptions = new RequestOptions();
                    //requestOptions.dontAnimate();

                    //Glide.with(MainActivity.this).load(image_url).into(circleImage);

                    //from the former button

                    if (true /*the account already exists*/){
                        startActivity(new Intent(MainActivity.this, Home.class));
                    }else {
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(FIRST_NAME, firstName);
                        editor.putString(LAST_NAME, lastName);
                        editor.putString(ID, id);

                        //get the tag and questions master from the server
                        TagMaster tagmaster = new TagMaster();
                        Gson gson = new Gson();
                        String json = gson.toJson(tagmaster);
                        editor.putString(TAG_MASTER, json);

                        QuestionsMaster questionsMaster = new QuestionsMaster(tagmaster);
                        String qmJson = gson.toJson(questionsMaster);
                        editor.putString(QUESTION_MASTER, qmJson);

                        editor.apply();
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

    /**
     * If the user is already logged in we can go ahead
     * and load the information.
     */
    private void checkLoginStatus() {
        if (AccessToken.getCurrentAccessToken() != null) {
            loadAccount(AccessToken.getCurrentAccessToken());
        }
    }

}
