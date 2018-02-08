package com.example.edvardsen.quick.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        //TODO: EMAIL INPUT --- ASK SERVER FOR ID BY EMAIL
        Intent intent = new Intent(getBaseContext(), ChatActivity.class);
        intent.putExtra(DefaultValues.roomType, DefaultValues.roomTypeCouple);
        startActivity(intent);
    }
}
