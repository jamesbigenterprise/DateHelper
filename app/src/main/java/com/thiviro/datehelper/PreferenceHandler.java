package com.thiviro.datehelper;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;


public class PreferenceHandler{

  public static final String SHARED_PREFS = "sharedPrefs";
  public static final String FIRST_NAME = "first_name";
  public static final String LAST_NAME = "last_name";
  public static final String ID = "id";
  public static final String TAG_MASTER = "tag_master";
  public static final String QUESTION_MASTER = "question_master";
  public static final String ACCOUNT = "account";
  public static final String LOGIN = "login";
  public static final String PHOTO_URL= "Photo_URL";
  public static final String ERROR = "ERROR";
  public static final String GENDER_BOOLEAN = "gender_boolean";

  private Activity activity;
  private SharedPreferences activitySP;
  private String SPName;
  private SharedPreferences.Editor editor;
  private Gson gson;

  public PreferenceHandler(Activity activity){
    this.activity = activity;
    this.SPName = SHARED_PREFS;
    this.activitySP = activity.getSharedPreferences(SPName, Context.MODE_PRIVATE);
    this.editor = activitySP.edit();
    gson = new Gson();
  }

  public void setAccount(Account account){
    String accountJSON = gson.toJson(account);
    editor.putString(ACCOUNT, accountJSON);
    editor.apply();
  }

  public Account getAccount(){
    String account = activitySP.getString(ACCOUNT, ERROR);
    Account myAccount = gson.fromJson(account, Account.class);
    return myAccount;
  }

  public String getName(){
    return getAccount().getName();
  }

  public void setFirstName(String firstName){
    editor.putString(FIRST_NAME, firstName);
    editor.apply();
  }

  public String getFirstName(){
    return activitySP.getString(FIRST_NAME, ERROR);
  }

  public void setLastName(String lastName){
    editor.putString(LAST_NAME, lastName);
    editor.apply();
  }

  public String getLastName(){
    return activitySP.getString(LAST_NAME, ERROR);
  }

  public void setID(String id){
    editor.putString(ID, id);
    editor.apply();
  }

  public String getId(){
    return getAccount().getId();
  }

  public void setGender(boolean genderBoolean){
    editor.putBoolean(GENDER_BOOLEAN, genderBoolean);
    editor.apply();
  }

  public boolean getGender(){
    return activitySP.getBoolean(GENDER_BOOLEAN, false);
  }

  public void setLogin(String login){
    editor.putString(LOGIN, login);
    editor.apply();
  }

  public String getLogin(){
    return activitySP.getString(LOGIN, ERROR);
  }

  public void setPhotoURL(String photoURL){
    editor.putString(PHOTO_URL, photoURL);
    editor.apply();
  }

  public String getPhotoURL(){
    return activitySP.getString(PHOTO_URL, ERROR);
  }

  public void setTagMaster(TagMaster tagMaster){
    String tagMasterJSON = gson.toJson(tagMaster);
    editor.putString(TAG_MASTER, tagMasterJSON);
    editor.apply();
  }

  public TagMaster getTagMaster(){
    String tagMaster = activitySP.getString(TAG_MASTER, ERROR);
    return gson.fromJson(tagMaster, TagMaster.class);
  }

  public void setQuestionMaster(QuestionsMaster questionMaster){
    String questionMasterJSON = gson.toJson(questionMaster);
    editor.putString(QUESTION_MASTER, questionMasterJSON);
    editor.apply();
  }

  public QuestionsMaster getQuestionMaster(){
    String questionMaster = activitySP.getString(QUESTION_MASTER, ERROR);
    return gson.fromJson(questionMaster, QuestionsMaster.class);
  }

}

