package com.thiviro.datehelper;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * The APIQuestionWorker class retrieves the questions from
 * the backend service and populates the objects in the app
 */
public class APIQuestionWorker implements Runnable {

  public final static String HOST = "http://35.222.214.134:8080";
  public final static String ENDPOINT_QUESTIONS = HOST + "/api/questions";
  public final static String ENDPOINT_USERS = HOST + "/api/users";

  private WeakReference<Activity> activity;

  public APIQuestionWorker(Activity activity) {
    this.activity = new WeakReference<>(activity);
  }

  /**
   * The getAPIResponse method retrieves the API response after the service a request
   * has been done to the backend.
   *
   * @param endpoint API endpoint to retrieve the response in JSON format
   * @return the response of the API as a string containing the response JSON
   */
  public String getRequestResponse(String endpoint) {
    String JSON = "";
    InputStream response;

    try {
      HttpURLConnection webConnection = (HttpURLConnection) new URL(endpoint).openConnection();
      webConnection.setRequestMethod("GET");
      webConnection.setRequestProperty("Accept-Charset", "UTF-8");

      response = webConnection.getInputStream();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response));
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        stringBuilder.append(line);
      }

      JSON = stringBuilder.toString();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return JSON;
  }

  public String postRequestResponse(String body, String endpoint) {
    HttpURLConnection postConnection = null;
    try {
      postConnection = (HttpURLConnection) new URL(endpoint).openConnection();
      postConnection.setRequestMethod("POST");
      postConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

      postConnection.setRequestProperty("Content-Length", "" + body.getBytes().length);
      postConnection.setRequestProperty("Content-Language", "en-US");

      postConnection.setUseCaches(false);
      postConnection.setDoInput(true);
      postConnection.setDoOutput(true);

      //Send request
      DataOutputStream wr = new DataOutputStream(
          postConnection.getOutputStream());
      wr.writeBytes(body);
      wr.flush();
      wr.close();

      InputStream is = postConnection.getInputStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
      String line;
      StringBuilder response = new StringBuilder();
      while ((line = rd.readLine()) != null) {
        response.append(line);
        response.append('\r');
      }
      rd.close();
      return response.toString();
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    } finally {

      if (postConnection != null) {
        postConnection.disconnect();
      }

    }
  }

    @Override
    public void run () {
      System.out.println("Running in the background");
      final Activity activityRef = activity.get();
      //String response = getRequestResponse(ENDPOINT_GET_QUESTIONS);
      Person person =
          new Person("Rolando", "Rodriguez",Gender.MALE, new ArrayList<Tag>(), new TagMaster());
      Account account = new Account(person, "oijfoijfowef", "Picture.jpg");
      Question question = new Question(account, "My Question is", "Here comes the long version",
          new ArrayList<Tag>(), new TagMaster());
      String body = "{" +
          "\"id\": 25, \"userId\": 2345123123, \"title\": \"This is a test\", \"text\": \"Complete Question\"" +
          "}";
//      Gson gson = new Gson();
//      Type questionList = new TypeToken<ArrayList<Question>>() {
//      }.getType();
//      final List<Question> questionList1 = JSON.fromJson(response, questionList);
//      String JSON = gson.toJson(question);
      System.out.println(body);
      final String response = postRequestResponse(body, ENDPOINT_QUESTIONS);
      System.out.println("Response: " + response);
      activityRef.runOnUiThread(new Runnable() {
        @Override
        public void run() {

            //System.out.println(response);
//          for (Question q : questionList1) {
//            System.out.println(q.getSummary());
//          }
//          Toast.makeText(activityRef, "TEST", Toast.LENGTH_SHORT).show();
        }
      });
    }
  }
