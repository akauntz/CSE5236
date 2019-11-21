package com.example.mike9.cse_app;


public class DataCache {
    private static String firstName, email;

    public static void updateName(String name){
        firstName = name;
    }

    public static String getName(){
        return firstName;
    }

    public static void updateEmail(String em){email = em;}

    public static String getEmail() { return email;}
}
