package com.thiviro.datehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class GenderSelector extends AppCompatActivity implements View.OnClickListener {

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
    switch (view.getId()){
      case R.id.male:
        startActivity(new Intent(this, InterestSelector.class));
        break;
      case R.id.female:
        startActivity(new Intent(this, InterestSelector.class));
        break;
    }
  }
}
