package com.example.edvardsen.quick.activities;

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
import android.widget.Toast;

import com.example.edvardsen.quick.data.User;
import com.example.edvardsen.quick.helpers.DefaultValues;
import com.example.edvardsen.quick.R;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.edvardsen.quick.helpers.DefaultValues.JSON;

public class SignUpActivity extends AppCompatActivity {

    Button signUpBtn;
    EditText signUpUserName;
    EditText signUpEmail;
    EditText signUpPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpBtn = findViewById(R.id.signUpBtn);
        signUpUserName = findViewById(R.id.signUpUserName);
        signUpEmail = findViewById(R.id.signUpEmail);
        signUpPassword = findViewById(R.id.signUpPassword);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signUpEmail.getText().toString();
                String pass = signUpPassword.getText().toString();
                String user = signUpUserName.getText().toString();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    new SignUpActivity.SignUp().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DefaultValues.rootUrl
                                    + ":"
                                    + DefaultValues.restPort
                                    + "/users/"
                            , email
                            , pass
                            , user);
                } else {
                    new SignUpActivity.SignUp().execute(DefaultValues.rootUrl
                                    + ":"
                                    + DefaultValues.restPort
                                    + "/users/"
                            , email
                            , pass);
                }
            }
        });
    }

    public class SignUp extends AsyncTask<String, Void, Response> {
        @Override
        protected Response doInBackground(String... params){
            Response response = null;
            String postBody = "{\n" +
                    "    \"email\": \"" + params[1] + "\",\n" +
                    "    \"password\": \"" + params[2] + "\",\n" +
                    "    \"username\": \"" + params[3] + "\"\n" +
                    "}";
            try {
                String url = params[0];
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(JSON, postBody);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                response = client.newCall(request).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response){
            super.onPostExecute(response);
            try{
                if(response == null){
                    //TODO: SOMETHING WENT WRONG, TRY AGAIN!
                }else{
                    if(response.code() == 200){
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        User user = User.getInstance();
                        user.setUserID(jsonObject.getString("_id"));
                        user.setUserName(jsonObject.getString("username"));
                        user.setEmail(jsonObject.getString("email"));
                        //TODO: GET LIST ROOMS
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
