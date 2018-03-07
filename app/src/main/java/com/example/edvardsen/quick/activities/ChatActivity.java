package com.example.edvardsen.quick.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.edvardsen.quick.R;
import com.example.edvardsen.quick.data.User;
import com.example.edvardsen.quick.web.IO;
import io.socket.client.Socket;

public class ChatActivity extends AppCompatActivity {

    private Socket socket;
    RelativeLayout relativeLayout;
    ScrollView scrollView;
    EditText message;
    ImageView sendmessage;
    String currRoomId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("testtest", "onCreate");
        setContentView(R.layout.activity_chat);

        User.getInstance();

        relativeLayout = findViewById(R.id.activity_chat_relativelayout);
        scrollView = findViewById(R.id.activity_chat_scrollview);
        message = findViewById(R.id.message_field_message);
        sendmessage = findViewById(R.id.message_field_send_message);
        IO.configureActivity(this);
        IO.configureLayout(relativeLayout);
        socket = IO.getSocket();
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i("testtest", "onPause");
        IO.getSocket().emit("privateexit", User.getCurrentRoom());
        currRoomId = User.getCurrentRoom();
        User.setCurrentRoom("");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("testtest", "onResume " + currRoomId);
        if (!currRoomId.isEmpty()){
            IO.getSocket().emit("privateenter", currRoomId);
            User.setCurrentRoom(currRoomId);
            currRoomId = "";
        }
    }

    public void send_message(View v) {
        //TODO: If cr/nl, then just print msg.
        String msg = message.getText().toString().trim();
        if (!msg.isEmpty()) {
            message.setText("");
            message.setHint("Write a message");
            try {
                socket.emit("privatemessage", User.getCurrentRoom(), User.getUserName(), msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getBaseContext(), "    The fuck you doing?\nWrite something you fool.", Toast.LENGTH_SHORT).show();
            message.setText("");
            message.setHint("Write a message");
        }
        //TODO: THIS DOESNT SCROLL ALL THE WAY DOWN
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }
}
