package com.thiviro.datehelper;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowResults extends AppCompatActivity {

    Button result1;
    Button result2;
    Button result3;
    List<Question> resultQuestions;
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);
        //get the 3 buttons
        result1 = (Button) findViewById(R.id.write_new_question);
        result2 = (Button) findViewById(R.id.result_2);
        result3 = (Button) findViewById(R.id.result_3);
        List<Button> resultButtons = new ArrayList<>();
        resultButtons.add(result1);
        resultButtons.add(result2);
        resultButtons.add(result3);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String resultsJson = sharedPreferences.getString(DateStudyAreaSelector.HELP_RESULTS, "");
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Question, Question>>(){}.getType();
        HashMap<Question, Question> results = gson.fromJson(resultsJson, type);
        int i = 0;
        for(Map.Entry<Question, Question> entryQuestion : results.entrySet()){
            if(entryQuestion != null & i < 3){
                resultButtons.get(i).setVisibility(View.VISIBLE);
                resultButtons.get(i).setText(entryQuestion.getKey().getSummary());
                //add onclick to view this question
            }
          i++;
        }

    }
}
