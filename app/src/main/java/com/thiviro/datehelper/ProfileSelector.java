package com.thiviro.datehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ProfileSelector extends AppCompatActivity implements View.OnClickListener {



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile_selector);
    ImageView publicProfile = findViewById(R.id.public_profile);
    ImageView incognitoProfile = findViewById(R.id.incognito_profile);

    publicProfile.setOnClickListener(this);
    incognitoProfile.setOnClickListener(this);

  }

  @Override
  public void onClick(View view) {
    switch (view.getId()){
      case R.id.public_profile:
        startActivity(new Intent(this, GenderSelector.class));
        break;
      case R.id.incognito_profile:
        startActivity(new Intent(this, GenderSelector.class));
        break;

    }
  }
}
