package com.thiviro.datehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);
    }

    public void askQuestion(View view){
        Intent intent = new Intent(this, QuestionView.class);
        startActivity(intent);
    }
}
