package com.thiviro.datehelper;

import android.accessibilityservice.GestureDescription;
import android.app.Activity;
import android.os.AsyncTask;
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
 * The APIWorker class handles the interaction
 * with the backend server, making GET, POST and PUT requests
 *
 * @author Rolando
 * @version 1.0
 */
public class APIWorker extends AsyncTask<Void, String, String>{


  // Constant Definition

  public static final String HOST = "http://35.222.214.134:8080";
  public static final String ENDPOINT_QUESTIONS = HOST + "/api/questions";
  public static final String ENDPOINT_USERS = HOST + "/api/users";
  public static final String ENDPOINT_COMMENTS = HOST + "/api/comments";
  public static final String ENDPOINT_COMMENTS_BY_QUESTION = HOST + "/api/comments/question/";
  public static final String GET = "GET";
  public static final String POST = "POST";
  public static final String PUT = "PUT";

  // Variable Definition
  protected WeakReference<Activity> activity;
  protected String method;
  protected String endpoint;
  protected String requestBody;
  protected Object objectToJSON;
  protected String response;

  /**
   * Non-default constructor for an APIWorker. Requires the activity,
   * the API endpoint and the request method.
   *
   * @param activity  Activity that will be calling the worker object
   * @param endpoint  API endpoint to contact
   * @param method    Request method: GET, POST, PUT
   */
  public APIWorker(Activity activity, String endpoint, String method) {
    this.activity = new WeakReference<>(activity);
    this.method = method;
    this.endpoint = endpoint;
  }

  /**
   * Non-default constructor for an APIWorker. Requires the activity,
   * the API endpoint, the request method, and the request body.
   *
   * @param activity      Activity that will be calling the worker object
   * @param endpoint      API endpoint to contact
   * @param method        Request method: GET, POST, PUT
   * @param requestBody   Request content
   */
  public APIWorker(Activity activity, String endpoint, String method, String requestBody) {
    this.activity = new WeakReference<>(activity);
    this.method = method;
    this.endpoint = endpoint;
    this.requestBody = requestBody;
  }

  /**
   * Non-default constructor for an APIWorker. Requires the activity,
   * the API endpoint, the request method, and an object
   *
   * @param activity      Activity that will be calling the worker object
   * @param endpoint      API endpoint to contact
   * @param method        Request method: GET, POST, PUT
   * @param object        Object to be populate with data from the API.
   */
  public APIWorker(Activity activity, String endpoint, String method, Object object) {
    this.activity = new WeakReference<>(activity);
    this.method = method;
    this.endpoint = endpoint;
    this.requestBody = null;
    this.objectToJSON = object;
  }

  /**
   * The getRequestResponse method retrieves the API GET response after the service a request
   * has been done to the backend.
   *
   * @param endpoint  API endpoint to retrieve the response in JSON format
   * @return          API GET method response of the API as a string containing the response JSON
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

  /**
   *  The postRequestResponse method retrieves the API POST response after a request to the
   *  back-end has been done.
   *
   * @param body      Request body
   * @param endpoint  API endpoint
   * @return          API POST method response containing the reponse JSON.
   */
  public String postRequestResponse(String body, String endpoint) {
    HttpURLConnection postConnection = null;
    try {
      postConnection = (HttpURLConnection) new URL(endpoint).openConnection();
      postConnection.setRequestMethod("POST");
      postConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

      postConnection.setRequestProperty("Content-Length", "" + body.getBytes("UTF-8").length);
      postConnection.setRequestProperty("Content-Language", "en-US");

      postConnection.setUseCaches(false);
      postConnection.setDoInput(true);
      postConnection.setDoOutput(true);

      //Send request
      DataOutputStream wr = new DataOutputStream(
          postConnection.getOutputStream());
      wr.write(body.getBytes("UTF-8"));
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
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    } finally {

      if (postConnection != null) {
        postConnection.disconnect();
      }

    }
  }


  /**
   * The putRequestResponse method retrieves the API PUT response after a request to the
   * back-end has been done.
   *
   * @param body      Request body
   * @param endpoint  API endpoint
   * @return          API PUT method response containing the response JSON.
   */
  public String putRequestResponse(String body, String endpoint) {
    HttpURLConnection postConnection = null;
    try {
      postConnection = (HttpURLConnection) new URL(endpoint).openConnection();
      postConnection.setRequestMethod("PUT");
      postConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

      postConnection.setRequestProperty("Content-Length", "" + body.getBytes("UTF-8").length);
      postConnection.setRequestProperty("Content-Language", "en-US");

      postConnection.setUseCaches(false);
      postConnection.setDoInput(true);
      postConnection.setDoOutput(true);

      //Send request
      DataOutputStream wr = new DataOutputStream(
          postConnection.getOutputStream());
      wr.write(body.getBytes("UTF-8"));
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
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    } finally {

      if (postConnection != null) {
        postConnection.disconnect();
      }

    }
  }

  @Override
  protected String doInBackground(Void... voids) {
    System.out.println("Running in the background");
    final Activity activityRef = activity.get();
    response = "";
    Gson gson = new Gson();

    switch (method) {
      case POST:
        if (objectToJSON == null) {
          response = postRequestResponse(requestBody, endpoint);
        } else {
          String request = gson.toJson(objectToJSON);
          response = postRequestResponse(request, endpoint);
        }
        break;
      case PUT:
        if (objectToJSON == null) {
          response = putRequestResponse(requestBody, endpoint);
        } else {
          String request = gson.toJson(objectToJSON);
          response = putRequestResponse(request, endpoint);
        }
        break;
      case GET:
        response = getRequestResponse(endpoint);
        break;
    }
    return response;
  }

  /**
   * onPostExecute method, executes the instructions
   * that will be performed after the background
   * thread runs. This method is executed on the
   * User Interface (UI).
   *
   * @param response  response from the instructions
   *                  executed on the background
   */
  @Override
  protected void onPostExecute(String response) {
    final Activity activityRef = activity.get();
    Toast.makeText(activityRef, response, Toast.LENGTH_SHORT).show();
  }



}