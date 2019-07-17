package com.thiviro.datehelper;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class TestData implements Runnable {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TAG = "TestDada";
    private WeakReference<Home> activityWeakReference;

    public TestData(Home home)
    {
        this.activityWeakReference = new WeakReference<>(home);
    }

    public void run() {

        Home home = activityWeakReference.get();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(home);
        Gson gson = new Gson();

        Log.d(TAG, "Starting..");
        TagMaster tagMaster = new TagMaster();
        QuestionsMaster questionsMaster = new QuestionsMaster(tagMaster);

        //sample data for tests
        //test data

        //20 Tags
        Tag myTag1 = new Tag("Pets");
        Tag myTag2 = new Tag("Dogs");
        Tag myTag3 = new Tag("Cats");
        Tag myTag4 = new Tag("Movies");
        Tag myTag5 = new Tag("Marvel");
        Tag myTag6 = new Tag("Sports");
        Tag myTag7 = new Tag("Bike");
        Tag myTag8 = new Tag("Swim");
        Tag myTag9 = new Tag("Soccer");
        Tag myTag10 = new Tag("Golf");
        Tag myTag11 = new Tag("Human");
        Tag myTag12 = new Tag("Tech");
        Tag myTag13 = new Tag("Outdoor");
        Tag myTag14 = new Tag("Food");
        Tag myTag15 = new Tag("Spiritual");
        Tag myTag16 = new Tag("Library");
        Tag myTag17 = new Tag("BYUI");
        Tag myTag18 = new Tag("Devotional");
        Tag myTag19 = new Tag("Church");
        Tag myTag20 = new Tag("Ice Cream");

        Log.d(TAG, "Created 20 Tags");
        tagMaster.addTag(myTag1);
        tagMaster.addTag(myTag2);
        tagMaster.addTag(myTag3);
        tagMaster.addTag(myTag4);
        tagMaster.addTag(myTag5);
        tagMaster.addTag(myTag6);
        tagMaster.addTag(myTag7);
        tagMaster.addTag(myTag8);
        tagMaster.addTag(myTag9);
        tagMaster.addTag(myTag10);
        tagMaster.addTag(myTag11);
        tagMaster.addTag(myTag12);
        tagMaster.addTag(myTag13);
        tagMaster.addTag(myTag14);
        tagMaster.addTag(myTag15);
        tagMaster.addTag(myTag16);
        tagMaster.addTag(myTag17);
        tagMaster.addTag(myTag18);
        tagMaster.addTag(myTag19);
        tagMaster.addTag(myTag20);

        Log.d(TAG, "Added to the Tag Master");
        //5 batches of related tags
        List<Tag> animalTags = new ArrayList<Tag>();
        animalTags.add(tagMaster.getAllTags().get(0));
        animalTags.add(tagMaster.getAllTags().get(1));
        animalTags.add(tagMaster.getAllTags().get(2));
        List <Tag> sportTags = new ArrayList<Tag>();
        sportTags.add(tagMaster.getAllTags().get(3));
        sportTags.add(tagMaster.getAllTags().get(4));
        sportTags.add(tagMaster.getAllTags().get(5));
        sportTags.add(tagMaster.getAllTags().get(6));
        sportTags.add(tagMaster.getAllTags().get(7));
        sportTags.add(tagMaster.getAllTags().get(8));
        sportTags.add(tagMaster.getAllTags().get(9));
        List <Tag> churchTags = new ArrayList<Tag>();
        churchTags.add(tagMaster.getAllTags().get(10));
        churchTags.add(tagMaster.getAllTags().get(11));
        churchTags.add(tagMaster.getAllTags().get(12));
        churchTags.add(tagMaster.getAllTags().get(13));
        churchTags.add(tagMaster.getAllTags().get(14));
        List <Tag> otherTags = new ArrayList<Tag>();
        otherTags.add(tagMaster.getAllTags().get(15));
        otherTags.add(tagMaster.getAllTags().get(16));
        otherTags.add(tagMaster.getAllTags().get(17));
        otherTags.add(tagMaster.getAllTags().get(18));

        Person firstUser = new Person("Thiago", "Alves da Silva", true, churchTags, tagMaster);
        Account firstAccount = new Account(firstUser, "01");


        Question myQ1 = new Question(firstAccount,"Should I take her to the movies?", "Go to the movies",otherTags, tagMaster );
        questionsMaster.addQuestion(myQ1);
        Question myQ2 = new Question(firstAccount,"Should I Kiss her on the first date?", "Kiss on the first date",otherTags, tagMaster );
        questionsMaster.addQuestion(myQ2);
        Question myQ3 = new Question(firstAccount,"Should I spend any money on the first date?", "Spend money",otherTags, tagMaster );
        questionsMaster.addQuestion(myQ3);
        Question myQ4 = new Question(firstAccount,"Do you think it is a good idea to walk her home?", "Walk home",otherTags, tagMaster);
        questionsMaster.addQuestion(myQ4);
        Question myQ5 = new Question(firstAccount,"Should we play sports?", "Play Sports",sportTags, tagMaster );
        questionsMaster.addQuestion(myQ5);
        Question myQ6 = new Question(firstAccount,"Go for a walk is a good idea?", "Go for a walk",sportTags, tagMaster );
        questionsMaster.addQuestion(myQ6);
        Question myQ7 = new Question(firstAccount,"The aquarium is a good idea?", "Go to the aquarium",sportTags, tagMaster );
        questionsMaster.addQuestion(myQ7);
        Question myQ8 = new Question(firstAccount,"Walk her pet is a good idea?", "Walk pet", animalTags, tagMaster);
        questionsMaster.addQuestion(myQ8);
        Question myQ9 = new Question(firstAccount,"Devotional is a Date?", "Go for devotional",churchTags, tagMaster );
        questionsMaster.addQuestion(myQ9);
        Question myQ10 = new Question(firstAccount,"Institute is a Date?", "Go to institute",churchTags , tagMaster);
        questionsMaster.addQuestion(myQ10);

        Log.d(TAG, "Added 10 questions to the Questions master");
        //check question master
        for (Question question : questionsMaster.getQuestionsMasterMap().values()){
            System.out.println(question.getQuestion());
        }
        //create a girl to do some voting
        Person secondUser = new Person("Maiane", "Sampaio", false, otherTags, tagMaster);
        Account secondAccount = new Account(secondUser, "02");
        for (Question question : questionsMaster.getQuestionsMasterMap().values()){
            question.upVote(secondAccount, tagMaster);
        }
        for (Question question : questionsMaster.getQuestionsMasterMap().values()){
            question.upVote(secondAccount, tagMaster);
        }
        //create a second girl to do some voting
        Person thirdUser = new Person("Tcharla", "Marques", false, otherTags, tagMaster);
        Account thirdAccount = new Account(thirdUser, "03");

        for (Question question : questionsMaster.getQuestionsMasterMap().values()){
            question.upVote(thirdAccount, tagMaster);
        }

        Log.d(TAG, "Voting Done");
        //put everything back in the shared prefs
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //String questionsJson = gson.toJson(questionsMaster);
        String tagsJson = gson.toJson(tagMaster);
        //editor.putString(MainActivity.QUESTION_MASTER, questionsJson);
        //editor.putString(MainActivity.TAG_MASTER, tagsJson);
        //editor.apply();
        //*/


    }


}
