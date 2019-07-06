package com.thiviro.datehelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class GenderSelector extends AppCompatActivity implements View.OnClickListener {

  public static final String GENDER_BOOLEAN = "gender_boolean";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gender_selector);
    ImageView male = findViewById(R.id.male);
    ImageView female = findViewById(R.id.female);

    male.setOnClickListener(this);
    female.setOnClickListener(this);

  }

  @Override
  public void onClick(View view) {
      SharedPreferences sharedPreferences = getSharedPreferences(InterestSelector.SHARED_PREFS, MODE_PRIVATE);
      SharedPreferences.Editor editor = sharedPreferences.edit();
      switch (view.getId()){
      case R.id.male:
          editor.putBoolean(GENDER_BOOLEAN, true);
          editor.apply();
        startActivity(new Intent(this, InterestSelector.class));
        break;
      case R.id.female:
        editor.putBoolean(GENDER_BOOLEAN, true);
          editor.apply();
        startActivity(new Intent(this, InterestSelector.class));
        break;
    }
  }
}
