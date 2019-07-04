package com.thiviro.datehelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

  public static final Scanner scanner = new Scanner(System.in);
  public static void main(String[] args) {

    //test data
    TagMaster tagsMaster = new TagMaster(); // save the unique tags here
    QuestionsMaster questionsMaster = new QuestionsMaster(tagsMaster); // save the unique questions here


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

    //5 batches of related tags
    List <Tag> animalTags = new ArrayList<Tag>();
    animalTags.add(myTag1);
    animalTags.add(myTag2);
    animalTags.add(myTag3);
    List <Tag> sportTags = new ArrayList<Tag>();
    sportTags.add(myTag6);
    sportTags.add(myTag7);
    sportTags.add(myTag8);
    sportTags.add(myTag8);
    sportTags.add(myTag9);
    sportTags.add(myTag10);
    sportTags.add(myTag13);
    List <Tag> churchTags = new ArrayList<Tag>();
    churchTags.add(myTag15);
    churchTags.add(myTag16);
    churchTags.add(myTag17);
    churchTags.add(myTag18);
    churchTags.add(myTag19);
    List <Tag> otherTags = new ArrayList<Tag>();
    otherTags.add(myTag20);
    otherTags.add(myTag11);
    otherTags.add(myTag12);
    otherTags.add(myTag14);



    Person firstUser = new Person("Thiago", "Alves da Silva", true, churchTags, tagsMaster);
    Account firstAccount = new Account(firstUser);

    //10 Questions
    //author
    //question
    //summary

    System.out.println("Creating 10 questions");

    Question myQ1 = new Question(firstAccount,"Should I take her to the movies?", "Go to the movies",otherTags, tagsMaster );
    myQ1 = questionsMaster.addQuestion(myQ1);
    Question myQ2 = new Question(firstAccount,"Should I Kiss her on the first date?", "Kiss on the first date",otherTags, tagsMaster );
    myQ2 = questionsMaster.addQuestion(myQ2);
    Question myQ3 = new Question(firstAccount,"Should I spend any money on the first date?", "Spend money",otherTags, tagsMaster );
    myQ3 = questionsMaster.addQuestion(myQ3);
    Question myQ4 = new Question(firstAccount,"Do you think it is a good idea to walk her home?", "Walk home",otherTags, tagsMaster);
    myQ4 = questionsMaster.addQuestion(myQ4);
    Question myQ5 = new Question(firstAccount,"Should we play sports?", "Play Sports",sportTags, tagsMaster );
    myQ5 = questionsMaster.addQuestion(myQ5);
    Question myQ6 = new Question(firstAccount,"Go for a walk is a good idea?", "Go for a walk",sportTags, tagsMaster );
    myQ6 = questionsMaster.addQuestion(myQ6);
    Question myQ7 = new Question(firstAccount,"The aquarium is a good idea?", "Go to the aquarium",sportTags, tagsMaster );
    myQ7 = questionsMaster.addQuestion(myQ7);
    Question myQ8 = new Question(firstAccount,"Walk her pet is a good idea?", "Walk pet", animalTags, tagsMaster);
    myQ8 = questionsMaster.addQuestion(myQ8);
    Question myQ9 = new Question(firstAccount,"Devotional is a Date?", "Go for devotional",churchTags, tagsMaster );
    myQ9 = questionsMaster.addQuestion(myQ9);
    Question myQ10 = new Question(firstAccount,"Institute is a Date?", "Go to institute",churchTags , tagsMaster);
    myQ10 = questionsMaster.addQuestion(myQ10);

    //check the tagmaster
    System.out.println("Check all the tags saved, the total number is == " + tagsMaster.getAllTags().size());
    for (Tag tag : tagsMaster.getAllTags()){
      System.out.println(tag.getTagName());
    }

    //check question master
    System.out.println("Check all the questions saved, the total number is == " + questionsMaster.getQuestionsMasterMap().size());
    for (Question question : questionsMaster.getQuestionsMasterMap().values()){
      System.out.println(question.getQuestion());
    }

    //create a girl to do some voting
    Person secondUser = new Person("Maiane", "Sampaio", false, otherTags, tagsMaster);
    Account secondAccount = new Account(secondUser);
    //upVote all questions
    System.out.println("The girl will upVote all the questions, but before lets see the current votes ");
    for (Question question : questionsMaster.getQuestionsMasterMap().values()){
      System.out.println(question.getPercentOfUpVotes(secondUser));
      System.out.println(tagsMaster.getAllTags().size());
      System.out.println(tagsMaster.getAllTags().get(0).getPercentOfUpVotes(firstUser));
    }

    System.out.println("Girl voting ");
    for (Question question : questionsMaster.getQuestionsMasterMap().values()){
      question.upVote(secondAccount);
    }
    System.out.println("Guy cheking the votes");
    for (Question question : questionsMaster.getQuestionsMasterMap().values()){
      System.out.println(question.getPercentOfUpVotes(firstUser));
      System.out.println(tagsMaster.getAllTags().size());
      System.out.println(tagsMaster.getAllTags().get(0).getPercentOfUpVotes(firstUser));
    }
    System.out.println("Girl voting the same questions again (shouldn't be allowed ) ");
    for (Question question : questionsMaster.getQuestionsMasterMap().values()){
      question.upVote(secondAccount);
    }
    System.out.println("Guy checking the votes");
    for (Question question : questionsMaster.getQuestionsMasterMap().values()){
      System.out.println(question.getPercentOfUpVotes(firstUser));
      System.out.println(tagsMaster.getAllTags().size());
      System.out.println(tagsMaster.getAllTags().get(0).getPercentOfUpVotes(firstUser));
    }
    //create a second girl to do some voting
    Person thirdUser = new Person("Tcharla", "Marques", false, otherTags, tagsMaster);
    Account thirdAccount = new Account(thirdUser);

    System.out.println("Second Girl voting");
    for (Question question : questionsMaster.getQuestionsMasterMap().values()){
      question.upVote(thirdAccount);
    }
    System.out.println("Guy checking the votes");
    for (Question question : questionsMaster.getQuestionsMasterMap().values()){
      System.out.println(question.getPercentOfUpVotes(firstUser) + " UPVOTES for the question");
      System.out.println(tagsMaster.getAllTags().size()+ " TOTAL number of tags");
      System.out.println(tagsMaster.getAllTags().get(0).getPercentOfUpVotes(firstUser) + " first tag PERCENTAGE OF UPVOTES");
    }
    /*
    System.out.println("Second Girl down voting");
    for (Question question : questionsMaster.getQuestionsMasterMap().values()){
      question.downVote(thirdAccount);
    }
    */
    System.out.println("Guy checking the votes");
    for (Question question : questionsMaster.getQuestionsMasterMap().values()){
      System.out.println(question.getPercentOfUpVotes(firstUser));
    }




    //help me on a date
    List<Tag> tags3 = new ArrayList<>(animalTags);
    tags3.addAll(otherTags);
    System.out.println("Finally lets get help on a date, create the date profile and see if we get any results");
    Person dateProfile = new Person(firstAccount, tags3, tagsMaster);

    System.out.println("Number of questions in an random tag == " + tagsMaster.getAllTags().get(0).getNumberOfQuestion());
    System.out.println("Here is the first question " + tagsMaster.getAllTags().get(0).getTopQuestion(dateProfile).getSummary());

    Map<Question, Question> results = questionsMaster.helpOnDate(dateProfile);
    System.out.println("Size of results == " + results.size());

    if(results != null){
      System.out.println("Here are your results:");
      for(Question question : results.values()) {
        System.out.println("Summary == " + question.getSummary());
      }
    }else {
      System.out.println("Sorry, no suggestions, try asking a new question");
    }

  }

       //Ask A Question
}




