package com.thiviro.datehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_other);

    Button facebookLogin = findViewById(R.id.button_facebook);
    Button googleLogin = findViewById(R.id.button_google);

    facebookLogin.setOnClickListener(this);
    googleLogin.setOnClickListener(this);

  }

  @Override
  public void onClick(View view) {
    switch (view.getId()){
      case R.id.button_facebook:
        //TODO login using facebook
        System.out.println("FACEBOOK LOGIN");
        startActivity(new Intent(this, ProfileSelector.class));
        break;
      case R.id.button_google:
        //TODO login using google
        System.out.println("GOOGLE LOGIN");
        startActivity(new Intent(this, ProfileSelector.class));
        break;
    }
  }

}
