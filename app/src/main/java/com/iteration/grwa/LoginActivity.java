package com.iteration.grwa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText txtUName,txtPassword;
    Button btnLogin;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUName = (EditText)findViewById(R.id.txtUName);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        session = new SessionManager(getApplicationContext());

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetLoginApi loginApi = new GetLoginApi();
                loginApi.execute();

            }
        });

    }

    private class GetLoginApi extends AsyncTask<String,Void,String> {

        String status,message,UserId,UserName,UserPic,UserEmail,UserMobile,UserPassword;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("email",txtUName.getText().toString());
                joUser.put("pass",txtPassword.getText().toString());
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"Login.php",joUser.toString());
                JSONObject j = new JSONObject(pdUser);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    message=j.getString("message");
                    JSONArray JsArry=j.getJSONArray("Employee");
                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);

                        UserId =jo.getString("id");
                        UserName =jo.getString("name");
                        UserPic =jo.getString("pic");
                        UserEmail =jo.getString("email");
                        UserMobile =jo.getString("mobile");
                        UserPassword =jo.getString("pass");

                    }
                }
                else
                {
                    message=j.getString("message");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if(status.equals("1"))
            {

                session.createLoginSession(UserId,UserName,UserPic,UserEmail,UserMobile,UserPassword);

                Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
