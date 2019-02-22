package com.iteration.grwa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SessionManager session;
    static String BASE_URL = "http://grwatest.iterationtechnology.com/";
    int flag = 0;
    LinearLayout lnSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());
        flag = session.checkLogin();

        lnSnackbar = (LinearLayout)findViewById(R.id.lnSnackbar);

        lnSnackbar.setVisibility(View.GONE);

        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
        {
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)
                        && ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        && ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CALL_PHONE))
                {
                    SetThread();
                }
                else
                {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.CALL_PHONE"}, 200);
                    Thread back = new Thread()
                    {
                        public void run()
                        {
                            try {
                                sleep(8*1000);

                                if (flag == 1)
                                {
                                    Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                                else
                                {
                                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                }

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    back.start();
                }
            }
            else
            {
                SetThread();
            }

        }
        else
        {
            lnSnackbar.setVisibility(View.VISIBLE);
            lnSnackbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }

    }

    private void SetThread() {
        Thread background = new Thread()
        {
            public void run()
            {
                try {
                    sleep(5*1000);

                    if (flag == 1)
                    {
                        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(i);
                        finish();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        background.start();
    }
}
