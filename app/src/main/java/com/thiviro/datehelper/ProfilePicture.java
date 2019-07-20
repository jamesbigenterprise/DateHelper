package com.thiviro.datehelper;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class ProfilePicture extends AppCompatActivity implements View.OnClickListener {

  private ImageView profilePhoto;

  private Button next;
  private Account account;
  private Switch editProfile;
  private EditText firstName;
  private EditText lastName;
  private EditText studyArea;
  private EditText interests;
  private Spinner genderSpinner;
  private List<View> profileViews;
  private PreferenceHandler prefHandler;
  private final String[] GENDER = {"Male", "Female"};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile_picture);
    prefHandler = new PreferenceHandler(this);
    String imageURL = prefHandler.getPhotoURL();
    account =  prefHandler.getAccount();

    next = findViewById(R.id.picture_next);
    editProfile = findViewById(R.id.edit_switch);
    profilePhoto = findViewById(R.id.picture_profile_pic);
    firstName = findViewById(R.id.profile_first_name);
    lastName = findViewById(R.id.profile_last_name);
    studyArea = findViewById(R.id.profile_study_area);
    interests = findViewById(R.id.profile_interest);
    genderSpinner = findViewById(R.id.spinner_gender);
    genderSpinner.setAdapter(new ArrayAdapter<>(this,
        R.layout.spinner_gender, GENDER));


    // Populate fields with current values
    firstName.setText(prefHandler.getFirstName());
    lastName.setText(prefHandler.getLastName());
    genderSpinner.setSelection(account.getGender() ? 0 : 1);

    profileViews = new ArrayList<>();
    profileViews.add(firstName);
    profileViews.add(lastName);
    profileViews.add(studyArea);
    profileViews.add(interests);
    profileViews.add(genderSpinner);

    for(View et : profileViews) {
      et.setFocusable(false);
      et.setClickable(false);
      et.setEnabled(false);
    }

    editProfile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


        for(View et : profileViews){
          if (b){
            et.setFocusableInTouchMode(true);
            et.setFocusable(true);
            et.setClickable(true);
            et.setEnabled(true);
          }
          else{
            et.setFocusable(false);
            et.setEnabled(false);
          }
        }

      }
    });
    next.setOnClickListener(this);
    Glide.with(this).load(imageURL).into(profilePhoto);
  }


  @Override
  public void onClick(View view) {
    switch (view.getId()){

      case R.id.picture_next:
        startActivity(new Intent(this, Home.class));
        break;

    }
  }


}
