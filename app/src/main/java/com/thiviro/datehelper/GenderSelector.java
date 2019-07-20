package com.thiviro.datehelper;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * GenderSelector class:
 * The gender activity class present the options to the user
 * for him / her to select their gender.
 *
 * Implements the OnClickListener interface to control
 * the behavior when views are clicked
 *
 * @author Rolando, Thiago, Vitalii
 * @version 1.0
 */
public class GenderSelector extends AppCompatActivity implements View.OnClickListener {

  /**
   * The onCreate method sets the content view and creates references
   * to the objects in the screen for later interaction. The objects
   * onclick listeners a defined to be this same class.
   *
   * @param savedInstanceState Instance saved used to restore the app status when
   *                           a change occurs
   */

  public PreferenceHandler prefHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gender_selector);
    prefHandler = new PreferenceHandler(this);
    ImageView male = findViewById(R.id.male);
    ImageView female = findViewById(R.id.female);

    male.setOnClickListener(this);
    female.setOnClickListener(this);

  }

  /**
   * The onclick method includes the actions to be
   * executed when the items are clicked. For this class
   * the next activities in the workflow are called.
   *
   * @param view view clicked used to call the next activity
   */
  @Override
  public void onClick(View view) {


    switch (view.getId()){
      case R.id.male:
        prefHandler.setGender(Gender.MALE);
        startActivity(new Intent(this, InterestSelector.class));
        break;
      case R.id.female:
        prefHandler.setGender(Gender.FEMALE);
        startActivity(new Intent(this, InterestSelector.class));
        break;
    }
  }
}
