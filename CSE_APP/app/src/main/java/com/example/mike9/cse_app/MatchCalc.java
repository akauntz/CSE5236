package com.example.mike9.cse_app;

public class MatchCalc {
    private static int NUM_QUESTIONS = 3;
    public static float getPercent(int x, int y){
        int questionMatch = x^y;
        int i;
        float percent = 0;
        for(i=0;i<NUM_QUESTIONS;i++){
            if(questionMatch%2 == 0){
                percent++;
            }
            questionMatch /= 2;
        }

        percent*=100;
        percent/=NUM_QUESTIONS;
        return percent;
    }
}
