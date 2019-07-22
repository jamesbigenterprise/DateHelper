package com.thiviro.datehelper;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowResults extends AppCompatActivity {

    private ArrayAdapter<Button> listAdapter;
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

        result1 = findViewById(R.id.EditTextNewQuestion);
        result2 = findViewById(R.id.result_2);
        result3 = findViewById(R.id.result_3);
        List<Button> resultButtons = new ArrayList<>();
        resultButtons.add(result1);
        resultButtons.add(result2);
        resultButtons.add(result3);

        String resultsJson = getIntent().getStringExtra(DateStudyAreaSelector.HELP_RESULTS);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Question>>(){}.getType();
        List<Question> results = gson.fromJson(resultsJson, type);
        int i = 0;
        for (Question qt : results){
            if (i > results.size()){
                break;
            }
            resultButtons.get(i).setText(qt.getSummary());
            resultButtons.get(i).setVisibility(View.VISIBLE);
            String questionJson = new Gson().toJson(qt);
            final Intent intent = new Intent(ShowResults.this, QuestionView.class);
            intent.putExtra(NewQuestion.NEW_QUESTION_EXTRA, questionJson);
            resultButtons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  startActivity(intent);
                }
            });
            i++;
        }
    }


    /**
     * The askaQuestion method launches the activity
     * to start asking questions. It calls the NewQuestion class
     *
     * @param view view clicked used to call the next activity
     */
    public void askaQuestion(View view) {


        Intent intent = new Intent(this, NewQuestion.class);
        startActivity(intent);
    }

}
