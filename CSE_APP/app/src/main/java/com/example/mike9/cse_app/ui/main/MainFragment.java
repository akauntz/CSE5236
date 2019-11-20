package com.example.mike9.cse_app.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mike9.cse_app.DataCache;
import com.example.mike9.cse_app.HomeActivity;
import com.example.mike9.cse_app.MatchCalc;
import com.example.mike9.cse_app.QuestionsActivity;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.ShowMessage;
import com.example.mike9.cse_app.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainFragment extends Fragment implements View.OnClickListener {

    private MainViewModel mViewModel;
    private EditText email, password;

    public static MainFragment newInstance() {
        return new MainFragment();
    }
    public Map<String, String> user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        Log.d("TEST STUFF:", MatchCalc.getPercent(21, 10) + "");
        Log.d("TEST STUFF:", MatchCalc.getPercent(27, 10) + "");
        Log.d("TEST STUFF:", MatchCalc.getPercent(21, 27) + "");
        Button matchesButton = v.findViewById(R.id.matches_button);
        matchesButton.setOnClickListener((View.OnClickListener) this);
        Button signUpButton = v.findViewById(R.id.signUp_button);
        signUpButton.setOnClickListener(this);
        Button logInButton = v.findViewById(R.id.login_button);
        logInButton.setOnClickListener(this);
        email = v.findViewById(R.id.userLogin);
        password = v.findViewById(R.id.userPassword);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public void onClick(View v){
        final Activity activity = getActivity();
        switch (v.getId()){
            case R.id.login_button:
                final String userEmail = email.getText().toString();
                DocumentReference docRef = db.collection("users").document(userEmail);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    String storedPassword = document.get("password").toString();
                                    final String fName = document.get("name").toString();
                                    DataCache.updateName(fName);

                                    if (storedPassword.equals(password.getText().toString())) {
                                        Log.d(TAG, "Correct password");
                                        Log.d("Answer: ", document.get("answered?").toString());
                                        Log.d("Answer: ", document.get("answered?").toString().equals("false")+"");
                                        Log.d("Answer: ", document.get("answered?").toString().equals("true")+"");

                                        if (document.get("answered?").toString().equals("false")) {
                                            Intent questionsIntent = new Intent(activity, QuestionsActivity.class);
                                            questionsIntent.putExtra("EMAIL", email.getText().toString());
                                            questionsIntent.putExtra("NUMQUESTIONS", 0);
                                            startActivity(questionsIntent);
                                        } else {
                                            //logIn
                                            Intent logInIntent = new Intent(activity, HomeActivity.class);
                                            logInIntent.putExtra("EMAIL", email.getText().toString());
                                            logInIntent.putExtra("FIRSTNAME", fName);
                                            startActivity(logInIntent);
                                        }
                                    } else {
                                        Log.d(TAG, "Password Mismatch");
                                        ShowMessage.show(activity, "Incorrect Login");
                                    }

                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

                break;
            case R.id.signUp_button:
                startActivity(new Intent(activity, SignUpActivity.class));
                break;
        }

    }

}
