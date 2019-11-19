package com.example.mike9.cse_app;

public class MatchCalc {
    private static int NUM_QUESTIONS = 5;
    public static float getPercent(int x, int y){
        int questionMatch = x&y;
        float percent = 0;
        while(questionMatch > 0 ){
            percent += (questionMatch%2);
            questionMatch /= 2;
        }

        percent*=100;
        percent/=NUM_QUESTIONS;
        return percent;
    }
}
