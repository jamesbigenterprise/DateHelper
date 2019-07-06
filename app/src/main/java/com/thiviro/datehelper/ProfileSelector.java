package com.thiviro.datehelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ProfileSelector extends AppCompatActivity implements View.OnClickListener {

  public static final String SHARED_PREFS = "sharedPrefs";

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
        String firstName = "Incognito";
        String lastName = "";
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.FIRST_NAME, firstName);
        editor.putString(MainActivity.LAST_NAME, lastName);
        editor.apply();
        startActivity(new Intent(this, GenderSelector.class));
        break;

    }
  }
}
