package com.example.edvardsen.quick.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edvardsen.quick.data.User;
import com.example.edvardsen.quick.helpers.DefaultValues;
import com.example.edvardsen.quick.R;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    Button button;
    TextView tv;
    EditText etEmail;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.top_bar);
        setContentView(R.layout.activity_login);

        button = findViewById(R.id.loginActBtnLogin);
        tv = findViewById(R.id.loginActLinkSignup);
        etEmail = findViewById(R.id.loginEmail);
        etPassword = findViewById(R.id.loginPassword);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();
                new AuthAndRetrieve().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DefaultValues.rootUrl
                        + ":"
                        + DefaultValues.restPort
                        + "/users/?email="
                        + email
                        + "&password="
                        + pass);
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SignUpActivity.class));

            }
        });
    }

    public class AuthAndRetrieve extends AsyncTask<String, Void, Response> {
        @Override
        protected Response doInBackground(String... params) {
            Response response = null;
            try {
                String url = params[0];
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                response = client.newCall(request).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            try{
                if(response == null){
                    //TODO: SOMETHING WENT WRONG, TRY AGAIN!
                }else{
                    if(response.code() == 200){
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        Log.i("rspbody", jsonObject.toString());
                        User user = User.getInstance();
                        user.setUserID(jsonObject.getString("_id"));
                        user.setUserName(jsonObject.getString("username"));
                        user.setEmail(jsonObject.getString("email"));
                        //TODO: GET LISTROOMS
                        Toast.makeText(getBaseContext(), "Success!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getBaseContext(), CardMenuActivity.class));
                    }else{
                        Toast.makeText(getBaseContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                        Log.i("code", String.valueOf(response.code()));
                        Log.i("header", response.headers().toString());
                        Log.i("body", response.body().string());
                    }
                }
            }catch (Exception e) {e.printStackTrace();}
        }
    }
}
