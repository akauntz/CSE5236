package com.example.mike9.cse_app;


public class DataCache {
    private static String firstName;

    public static void updateName(String name){
        firstName = name;
    }

    public static String getName(){
        return firstName;
    }
}
