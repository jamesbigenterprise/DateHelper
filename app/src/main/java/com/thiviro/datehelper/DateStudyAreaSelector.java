package com.thiviro.datehelper;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the last page in the helponDate Workflow
 *
 * Here we will get the user Account from shared preferences and the tags selected in the previous
 * activity (DateInterestSelector).
 * The user will select another Tag and will click on Help on Date
 * it will start the CreateProfileAsyncTask, this task will download the TagMaster
 * create a Person profile for the date (that makes changes in the TagMasterthen)
 * upload the changed TagMaster to the server and return the Person object
 * On post execute it will call it will call helpOnDateTask()
 * This class will start a second Task: HelpOnDateAsyncTask
 * It will download the TagMaster again, create a questionMaster and call helpOnDate
 * The result will be a list of questions
 * on post execute of the second task call the method showResults
 * This method will create an intent with the list of results and start the
 * intent for the ShowResults Class
 */
public class DateStudyAreaSelector extends AppCompatActivity {

    private List<String> areas;
    private ListView studyArea;
    private Button helpOnDate;
    private ProgressBar progressBar;
    private String areaSelected;
    private Person profile;
    private Account userAccount;
    private ArrayAdapter<String> listAdapter;
    private PreferenceHandler prefHandler;

    public static final String SHARED_PREFS = "sharedPrefs";
    public final static String HELP_RESULTS = "help_results";
    private final static String LOG_DEBUG = "StudyArea()";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_DEBUG,"Startiing on create");
        setContentView(R.layout.activity_date_study_area_selector);
        prefHandler = new PreferenceHandler(this);
        areas = new ArrayList<>(Arrays.asList(getResources().
                getStringArray(R.array.study_area)));
        helpOnDate = findViewById(R.id.study_next_bt);
        studyArea = findViewById(R.id.studyArea);
        progressBar = findViewById(R.id.progress_bar);
        Log.d(LOG_DEBUG,"everything set ");
        createList();

        //get the user account
        userAccount = prefHandler.getAccount();


        Log.d(LOG_DEBUG,"on create done");
    }

    private void createList(){

        listAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, areas);
        studyArea.setAdapter(listAdapter);
        studyArea.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        studyArea.setItemChecked(0, true);
    }


    public void help(View v) {

                Log.d(LOG_DEBUG,"HELPONDATE Startng........");
                SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                Gson gson = new Gson();
                areaSelected = listAdapter.getItem(studyArea.getCheckedItemPosition());
                Tag studyAreaTag = new Tag(areaSelected);
                //add this Tag to the list of Tags

                Log.d(LOG_DEBUG,"HELPONDATE Middle........");
                String json =getIntent().getStringExtra(DateInterestsSelector.LIST_DATE_TAGS);
                Type type = new TypeToken<ArrayList<Tag>>() {}.getType();
                List<Tag> listOfTags = new ArrayList<Tag>();
                listOfTags = gson.fromJson(json, type);
                listOfTags.add(studyAreaTag);
                CreateProfileAsyncTask task1 = new CreateProfileAsyncTask(DateStudyAreaSelector.this);
                Log.d(LOG_DEBUG,"HELPONDATE before task");
                task1.execute(listOfTags);
    }

    private class CreateProfileAsyncTask extends AsyncTask<List<Tag>, Integer, Person> {
        private WeakReference<DateStudyAreaSelector> weakReference;
        private Account askerAccount;
        private TagMaster downloadedTagMaster;
        public CreateProfileAsyncTask (DateStudyAreaSelector context){
            weakReference = new WeakReference<DateStudyAreaSelector>(context);
            askerAccount = weakReference.get().userAccount;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            helpOnDate.setVisibility(View.INVISIBLE);
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }
        @Override
        protected Person doInBackground(List<Tag>... tags) {
            publishProgress(10);
            Gson gson = new Gson();
            TagMaster tagMaster = null;//
            publishProgress(20);
            /**
             * BACKEND
             * TODO download the tagmaster
             * Download the TagMaster
             */
            //----------------------------------------------------------------------------
            //For now I will create the TagMaster directly
            tagMaster = new TagMaster();
            //----------------------------------------------------------------------------
            downloadedTagMaster = tagMaster;
            publishProgress(50);
            Person person = new Person(askerAccount, tags[0], downloadedTagMaster);
            publishProgress(100);

            /**
             * BACKEND
             * //Todo Upload the TagMaster with the changes
             */
            return person;
        }

        @Override
        protected void onPostExecute(Person p) {
            super.onPostExecute(p);
            DateStudyAreaSelector context = weakReference.get();
            context.profile = p;
            context.helpOnDateTask();
        }

    }
    private void helpOnDateTask () {
      HelpOnDateAsyncTask task = new HelpOnDateAsyncTask(this);
      task.execute(profile);
    }

    private class HelpOnDateAsyncTask extends AsyncTask<Person, Integer, List<Question>> {
        private WeakReference<DateStudyAreaSelector> weakReference;


        public HelpOnDateAsyncTask(DateStudyAreaSelector weakReference) {
            this.weakReference = new WeakReference<>(weakReference);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }
        @Override
        protected List<Question> doInBackground(Person... persons) {
            publishProgress(10);
            List<Question> results = new ArrayList<Question>();
            QuestionsMaster questionsMaster = new QuestionsMaster();
            Gson gson = new Gson();
            TagMaster tagMaster = null;
            publishProgress(20);
            /**
             * BACKEND
             * TODO download the tagmaster
             * Download the TagMaster
             */
            tagMaster = new TagMaster();
            publishProgress(50);
            //---------------------------------------------
            //Test Remove latter
            Account testAccount = weakReference.get().userAccount;
            Tag tag1 = new Tag("Movies");
            Tag tag2 = new Tag("sports");
            Tag tag3 = new Tag("pets");
            ArrayList<Tag> otherTags = new ArrayList<>();
            otherTags.add(tag1);
            otherTags.add(tag2);
            otherTags.add(tag3);
            //As a test I will create the list of questions
            Question myQ1 = new Question(testAccount,"Should I take her to the movies?", "Go to the movies",otherTags, tagMaster );
            Question myQ2 = new Question(testAccount,"Should I Kiss her on the first date?", "Kiss on the first date",otherTags, tagMaster );
            Question myQ3 = new Question(testAccount,"Should I spend any money on the first date?", "Spend money",otherTags, tagMaster );
            ArrayList<Question> topQuestions = new ArrayList<>();
            topQuestions.add(myQ1);
            topQuestions.add(myQ2);
            topQuestions.add(myQ3);
            //---------------------------------------------
            //results = questionsMaster.helpOnDate(persons[0], tagMaster);
            results = topQuestions;
            publishProgress(100);
            /**
             * BACKEND
             * //Todo Upload the TagMaster with the changes
             */

            return results;
        }

        @Override
        protected void onPostExecute(List<Question> q) {
            super.onPostExecute(q);
            DateStudyAreaSelector context = weakReference.get();
            context.showResults(q);
        }
    }

    public void showResults(List<Question> results) {
        Gson gson = new Gson();
        String resultsJson = gson.toJson(results);
        Intent intent = new Intent(this,  ShowResults.class);
        intent.putExtra(HELP_RESULTS, resultsJson);
        startActivity(intent);
    }
}
