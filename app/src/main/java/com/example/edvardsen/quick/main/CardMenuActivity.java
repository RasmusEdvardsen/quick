package com.example.edvardsen.quick.main;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.edvardsen.quick.data.User;
import com.example.edvardsen.quick.R;
import com.example.edvardsen.quick.helpers.DefaultValues;
import com.example.edvardsen.quick.web.IO;
import io.socket.client.Socket;

public class CardMenuActivity extends AppCompatActivity {

    //TODO: FIX RECONNECTING ON LANDSCAPE/HORIZONTAL MODE CHANGE!

    private Socket socket;
    RelativeLayout relativeLayout;
    private static RelativeLayout personRelativeLayout;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.top_bar);
        setContentView(R.layout.activity_card_menu);

        User.getInstance();

        relativeLayout = findViewById(R.id.card_outer_relative_layout);
        personRelativeLayout = findViewById(R.id.card_inner_person_relative_layout);
        IO.configureActivity(this);
        IO.configureLayout(relativeLayout);
        IO.configurePrivateRoomLayout(personRelativeLayout);
        socket = IO.getSocket();
        socket.emit("getuserrooms", User.getUserID());
    }

    public void couple_room(View v){
        cardView = findViewById(R.id.card_person);
        final EditText editText = new EditText(this);
        final TextView textView = findViewById(R.id.card_person_text);
        editText.setId(View.generateViewId());
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: THIS IS GARBAGE CODE!
                String[] roomNameAndClientToCouple = editText.getText().toString().split(" ");
                //TODO: VALIDATE ON CLIENTCOUPLE! IF CLIENTCOUPLE IS NOT EMAIL VALIDATED, THEN TRY AGAIN!
                socket.emit("privatecreate", roomNameAndClientToCouple[0], roomNameAndClientToCouple[1]);
                Intent intent = new Intent(getBaseContext(), ChatActivity.class);
                intent.putExtra(DefaultValues.roomType, DefaultValues.roomTypePrivate);
                intent.putExtra("clientToCouple", roomNameAndClientToCouple[0]);
                startActivity(intent);
                //TODO: THIS IS SO EMBARRASSING!
            }
        });
        cardView.addView(editText);
    }
}
