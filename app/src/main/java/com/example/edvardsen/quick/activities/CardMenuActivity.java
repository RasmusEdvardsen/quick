package com.example.edvardsen.quick.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.RelativeLayout;

import com.example.edvardsen.quick.data.User;
import com.example.edvardsen.quick.R;
import com.example.edvardsen.quick.web.IO;
import io.socket.client.Socket;

public class CardMenuActivity extends AppCompatActivity {

    //TODO: FIX RECONNECTING ON LANDSCAPE/HORIZONTAL MODE CHANGE!

    private Socket socket;
    RelativeLayout relativeLayout;
    private RelativeLayout cardMenuRelativeLayout;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_menu);

        User.getInstance();

        relativeLayout = findViewById(R.id.card_outer_relative_layout);
        cardMenuRelativeLayout = findViewById(R.id.card_menu_relative_layout);
        IO.configureActivity(this);
        IO.configureOuterLayout(relativeLayout);
        IO.configureCardMenuLayout(cardMenuRelativeLayout);
        socket = IO.getSocket();
        socket.emit("getuserrooms", User.getUserID());
    }

    /*public void couple_room(View v){
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
    }*/
}
