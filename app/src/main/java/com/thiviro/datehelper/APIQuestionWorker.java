package com.thiviro.datehelper;

import android.app.Activity;
import android.widget.Toast;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

/**
 * The APIQuestionWorker class retrieves the questions from
 * the backend service and populates the objects in the app
 */
public class APIQuestionWorker implements Runnable {
  private WeakReference<Activity> activity ;

  public void APIQuestionWorker(Activity activity){
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

    try{
      URLConnection webConnection = new URL(endpoint).openConnection();
      webConnection.setRequestProperty("Accept-Charset", "UTF-8");
      response = webConnection.getInputStream();
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
    final Activity activityRef = activity.get();
    String response = getAPIResponse("localhost:8080/api/questions");
    Gson JSON = new Gson();
    // TODO need class to contain all questions as a list of questions
    activityRef.runOnUiThread(new Runnable(){
      @Override
      public void run() {
        //Toast.makeText(activityRef, "TEST" , Toast.LENGTH_SHORT).show();
      }
    });
  }
}
