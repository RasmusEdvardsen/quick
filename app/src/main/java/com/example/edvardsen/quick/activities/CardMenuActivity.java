package com.example.edvardsen.quick.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.edvardsen.quick.data.User;
import com.example.edvardsen.quick.R;
import com.example.edvardsen.quick.fragments.CreateRoom;
import com.example.edvardsen.quick.web.IO;
import io.socket.client.Socket;

public class CardMenuActivity extends AppCompatActivity {

    //TODO: FIX RECONNECTING ON LANDSCAPE/HORIZONTAL MODE CHANGE!

    Socket socket;
    RelativeLayout relativeLayout;
    RelativeLayout cardMenuRelativeLayout;


    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_menu);

        User.getInstance();

        editText = findViewById(R.id.message_field_message);
        relativeLayout = findViewById(R.id.card_outer_relative_layout);
        cardMenuRelativeLayout = findViewById(R.id.card_menu_relative_layout);
        IO.configureActivity(this);
        IO.configureOuterLayout(relativeLayout);
        IO.configureCardMenuLayout(cardMenuRelativeLayout);
        socket = IO.getSocket();
        socket.emit("getuserrooms", User.getUserID());
    }

    public void create_room_fragment(View v){
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.card_menu_relative_layout, new CreateRoom());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
    }
}
