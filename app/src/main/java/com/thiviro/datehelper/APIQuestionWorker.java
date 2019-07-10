package com.thiviro.datehelper;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * The APIQuestionWorker class retrieves the questions from
 * the backend service and populates the objects in the app
 */
public class APIQuestionWorker implements Runnable {
  private WeakReference<Activity> activity ;
  public static final String DEBUG_APIQuestionWorker =  "APIQuestionWorker";
  public APIQuestionWorker(Activity activity){
    this.activity = new WeakReference<>(activity);
  }

  /**
   * The getAPIResponse method retrieves the API response after the service a request
   * has been done to the backend.
   *
   * @param endpoint API endpoint to retrieve the response in JSON format
   * @return the response of the API as a string containing the response JSON
   */
  public String getAPIResponse(String endpoint){
    String JSON = "";
    InputStream response;
    Log.d(DEBUG_APIQuestionWorker,"getAPIResponse() Starting...");

    try{
      URLConnection webConnection = new URL(endpoint).openConnection();
      webConnection.setRequestProperty("Accept-Charset", "UTF-8");
      Log.d(DEBUG_APIQuestionWorker,"getAPIResponse() Variables set");
      response = webConnection.getInputStream();
      Log.d(DEBUG_APIQuestionWorker,"getAPIResponse() Connection established");
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response));
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while ((line = bufferedReader.readLine()) != null){
        stringBuilder.append(line);
      }

      JSON = stringBuilder.toString();
    } catch (Exception e){
      System.out.println(e.getMessage());
    }

    return JSON;
  }

  @Override
  public void run() {
    Log.d(DEBUG_APIQuestionWorker, "Starting runnable");
    System.out.println("Running in the background");
    final Activity activityRef = activity.get();
      Log.d(DEBUG_APIQuestionWorker, "Weak reference set, let us get a API Response");
    String response = getAPIResponse("http://192.168.0.12:8080/api/questions");
      Log.d(DEBUG_APIQuestionWorker, "Response received");
    Gson JSON = new Gson();
    Type questionList = new TypeToken<ArrayList<Question>>(){}.getType();
    final List<Question> questionList1 = JSON.fromJson(response, questionList);
    // TODO need class to contain all questions as a list of questions
    activityRef.runOnUiThread(new Runnable(){
      @Override
      public void run() {

        for (Question q : questionList1){
          System.out.println(q.getQuestion());
        }
        Toast.makeText(activityRef, "TEST" , Toast.LENGTH_SHORT).show();
      }
    });
  }
}
