package com.example.edvardsen.quick.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.edvardsen.quick.data.User;
import com.example.edvardsen.quick.R;
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
        socket.emit("private", User.getUserID());
    }
}
