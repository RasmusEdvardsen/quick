package com.example.edvardsen.quick.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.edvardsen.quick.R;
import com.example.edvardsen.quick.helpers.DefaultValues;
import com.example.edvardsen.quick.web.IO;
import io.socket.client.Socket;

public class ChatActivity extends AppCompatActivity {

    private Socket socket;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        relativeLayout = (RelativeLayout) findViewById(R.id.testrl);
        IO.configureActivity(this);
        IO.configureLayout(relativeLayout);
        socket = IO.getSocket();

        String roomType = getIntent().getExtras().getString(DefaultValues.roomType);
        String clientToCouple = getIntent().getExtras().getString("clientToCouple");

        Toast.makeText(getBaseContext(), roomType, Toast.LENGTH_LONG).show();
        Toast.makeText(getBaseContext(), clientToCouple, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("cardMenuAct", "onDestroy disconnect");
        socket.off();
        socket.disconnect();
    }
}
