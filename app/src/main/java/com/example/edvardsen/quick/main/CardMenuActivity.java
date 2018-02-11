package com.example.edvardsen.quick.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.edvardsen.quick.data.User;
import com.example.edvardsen.quick.R;
import com.example.edvardsen.quick.helpers.DefaultValues;
import com.example.edvardsen.quick.web.IO;
import io.socket.client.Socket;

public class CardMenuActivity extends AppCompatActivity {

    //TODO: FIX RECONNECTING ON LANDSCAPE/HORIZONTAL MODE CHANGE!

    private Socket socket;
    RelativeLayout relativeLayout;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_card_menu);

        User.getInstance();

        relativeLayout = (RelativeLayout) findViewById(R.id.testrl);
        IO.configureActivity(this);
        IO.configureLayout(relativeLayout);
        socket = IO.getSocket();
    }

    public void coupleRoom(View v){
        cardView = (CardView) findViewById(R.id.card_person);
        final EditText editText = new EditText(this);
        editText.setId(View.generateViewId());
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: THIS IS GARBAGE CODE!
                String[] roomNameAndClientToCouple = editText.getText().toString().split(" ");
                //TODO: VALIDATE ON CLIENTCOUPLE! IF CLIENTCOUPLE IS NOT EMAIL VALIDATED, THEN TRY AGAIN!
                socket.emit("privatejoin", roomNameAndClientToCouple[0], roomNameAndClientToCouple[1]);
                Intent intent = new Intent(getBaseContext(), ChatActivity.class);
                intent.putExtra(DefaultValues.roomType, DefaultValues.roomTypeCouple);
                intent.putExtra("clientToCouple", roomNameAndClientToCouple[0]);
                startActivity(intent);
                //TODO: THIS IS SO EMBARRASSING!
            }
        });
        cardView.addView(editText);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("cardMenuAct", "onDestroy disconnect");
        socket.off();
        socket.disconnect();
    }
}
