package com.example.edvardsen.quick.fragments;

/**
 * Created by Epico-u-01 on 3/20/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.edvardsen.quick.R;
import com.example.edvardsen.quick.activities.ChatActivity;
import com.example.edvardsen.quick.web.IO;

public class CreateRoom extends Fragment {
    AppCompatButton button;
    EditText room;
    EditText email;
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_create_room, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = view.findViewById(R.id.fragment_create_room_create_button);
        room = view.findViewById(R.id.fragment_create_room);
        email = view.findViewById(R.id.fragment_create_email);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roominput = room.getText().toString();
                String emailinput = email.getText().toString();
                //TODO: VALIDATE ON CLIENTCOUPLE! IF CLIENTCOUPLE IS NOT EMAIL VALIDATED, THEN TRY AGAIN!
                IO.getSocket().emit("privatecreate", roominput, emailinput);
                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                startActivity(intent);
            }
        });
    }
}