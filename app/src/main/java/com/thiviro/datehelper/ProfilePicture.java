package com.thiviro.datehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ProfilePicture extends AppCompatActivity implements View.OnClickListener {

  private ImageView profilePic;
  private Button crop;
  private Button next;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile_picture);

    profilePic = findViewById(R.id.picture_profile_pic);
    crop = findViewById(R.id.picture_select_button);
    next = findViewById(R.id.picture_next);

    profilePic.setOnClickListener(this);
    crop.setOnClickListener(this);
    next.setOnClickListener(this);

  }


  @Override
  public void onClick(View view) {
    switch (view.getId()){
      case R.id.picture_profile_pic:
        //TODO choose picture

        break;
      case R.id.picture_select_button:
        //TODO crop picture and save
        break;
      case R.id.picture_next:
        startActivity(new Intent(this, Home.class));
        break;
    }
  }
}
