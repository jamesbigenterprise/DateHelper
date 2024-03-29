package com.thiviro.datehelper;

import android.app.Activity;

/**
 * The UserPutAPIWorker handles just the PUT method portion
 * of communication with the API.
 *
 * @author Rolando
 */
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