package com.example.mohan.ooad;


public class QuestionLibrary {

    private String mQuestions[] = {
            "Which part of the plant holds it in the soil?",
            "This part of the plant absorbs energy from the sun.",
            "This part of the plant attracts bees, butterflies and hummingbirds.",
            "The _______ holds the plant upright.",
            "5+5/5x5-5 = ______"
    };


    private String mChoices[][] = {
            {"Roots", "Stem", "Flower"},
            {"Fruit", "Leaves", "Seeds"},
            {"Bark", "Flower", "Roots"},
            {"Flower", "Leaves", "Stem"},
            {"- 15", "5", "1 / 5",}
    };


    private String mCorrectAnswers[] = {"Roots", "Leaves", "Flower", "Stem", "5"};


    public String getQuestion(int a) {
        return mQuestions[a];
    }


    public String getChoice1(int a) {
        return mChoices[a][0];
    }


    public String getChoice2(int a) {
        return mChoices[a][1];
    }

    public String getChoice3(int a) {
        return mChoices[a][2];
    }

    public String getCorrectAnswer(int a) {
        String answer = mCorrectAnswers[a];
        return answer;
    }

}
