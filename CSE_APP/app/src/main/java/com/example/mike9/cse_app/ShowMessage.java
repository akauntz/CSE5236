package com.example.mike9.cse_app;

import android.content.Context;
import android.widget.Toast;

public class ShowMessage {
    public static void show(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
