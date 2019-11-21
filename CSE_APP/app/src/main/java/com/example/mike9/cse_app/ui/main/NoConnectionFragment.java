package com.example.mike9.cse_app.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mike9.cse_app.InternetCheck;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.ShowMessage;

public class NoConnectionFragment  extends Fragment implements View.OnClickListener {

    public static NoConnectionFragment newInstance() {
        return new NoConnectionFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.no_connection_fragment, container, false);
        Button backButton = v.findViewById(R.id.noInternetButton);
        backButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v){
        if(InternetCheck.isConnected(getActivity())){
            getActivity().finish();
        } else {
            ShowMessage.show(getActivity(), getActivity().getString(R.string.no_internet));
        }
    }
}
