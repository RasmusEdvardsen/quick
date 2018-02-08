package com.example.edvardsen.quick.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.edvardsen.quick.R;
import com.example.edvardsen.quick.helpers.DefaultValues;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String roomType = getIntent().getExtras().getString(DefaultValues.roomType);
        Toast.makeText(getBaseContext(), roomType, Toast.LENGTH_LONG).show();
    }
}
