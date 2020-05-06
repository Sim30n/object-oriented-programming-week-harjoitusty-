package com.example.olio_harjoitusty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class AddComment extends Fragment {

    String circuitID;
    EditText comment;
    EditText nick;
    Button submit;
    FirebaseGetDriver firebaseGetDriver = new FirebaseGetDriver();

    public AddComment(String circuitID) {
        this.circuitID = circuitID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_comment, container, false);
        comment = v.findViewById(R.id.edit_comment);
        nick = v.findViewById(R.id.edit_comment_name);
        submit = v.findViewById(R.id.submit_comment);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String strDate = dateFormat.format(date);
                String com = comment.getText().toString();
                String nic = nick.getText().toString();
                String toDatabase = com + " -" + nic + ", " + strDate;
                firebaseGetDriver.addComment(circuitID, toDatabase);
                Fragment newFrag = new CircuitInfoFragment(circuitID);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFrag ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        return v;
    }
}
