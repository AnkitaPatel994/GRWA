package com.iteration.grwa;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CircleImageView ivUserProfilePhoto;
    TextView txtProfileName,txtProfileEmail,txtProfileMobile;
    SessionManager session;
    Dialog dialog;
    EditText txtProfilePhone;
    CircleImageView ivUserImg;
    Bitmap bitmap = null;
    String encodedImgpath="",user_id;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(3).setChecked(true);

        session = new SessionManager(getApplicationContext());
        HashMap<String,String> user = session.getUserDetails();
        user_id = user.get(SessionManager.user_id);
        String user_name = user.get(SessionManager.user_name);
        String user_email = user.get(SessionManager.user_email);

        View headerview = navigationView.getHeaderView(0);
        ivUserImg = (CircleImageView)headerview.findViewById(R.id.ivUserImg);

        GetProfilePic profilePic = new GetProfilePic(ProfileActivity.this,user_id,ivUserImg);
        profilePic.execute();

        LinearLayout llNavProfile = (LinearLayout)headerview.findViewById(R.id.llNavProfile);
        llNavProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this,
                            Manifest.permission.CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        selectImage();
                    } else {
                        ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{"android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE"}, 200);
                        // No explanation needed, we can request the permission.
                    }
                } else {
                    selectImage();
                }
            }
        });

        TextView txtUserName = (TextView)headerview.findViewById(R.id.txtUserName);
        txtUserName.setText(user_name);

        TextView txtUserEmail = (TextView)headerview.findViewById(R.id.txtUserEmail);
        txtUserEmail.setText(user_email);

        ivUserProfilePhoto = (CircleImageView)findViewById(R.id.ivUserProfilePhoto);
        txtProfileName = (TextView)findViewById(R.id.txtProfileName);
        txtProfileEmail = (TextView)findViewById(R.id.txtProfileEmail);
        txtProfileMobile = (TextView)findViewById(R.id.txtProfileMobile);

        GetViewProfile viewProfile = new GetViewProfile();
        viewProfile.execute();
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (options[item].equals("Choose from Gallery")) {

                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == REQUEST_CAMERA)
            {
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                byte[] b = bytes.toByteArray();
                encodedImgpath = Base64.encodeToString(b, Base64.DEFAULT);

                ivUserImg.setImageBitmap(bitmap);

                GetSendImg sendImg = new GetSendImg(ProfileActivity.this,user_id,encodedImgpath);
                sendImg.execute();

            }
            else if (requestCode == SELECT_FILE)
            {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                byte[] b = bytes.toByteArray();
                encodedImgpath = Base64.encodeToString(b, Base64.DEFAULT);

                ivUserImg.setImageBitmap(bitmap);

                GetSendImg sendImg = new GetSendImg(ProfileActivity.this,user_id,encodedImgpath);
                sendImg.execute();

            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_profile_edit) {

            dialog = new Dialog(ProfileActivity.this,android.R.style.Theme_Light_NoTitleBar);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.profile_edit_dialog);
            dialog.setCancelable(true);

            txtProfilePhone=(EditText) dialog.findViewById(R.id.txtProfilePhone);

            Button btnProfileSubmit=(Button) dialog.findViewById(R.id.btnProfileSubmit);
            btnProfileSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GetEditProfileMobile editProfileMobile = new GetEditProfileMobile();
                    editProfileMobile.execute();
                }
            });

            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            Intent i = new Intent(ProfileActivity.this,HomeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_ap)
        {
            Intent i = new Intent(ProfileActivity.this,AddPropertyActivity.class);
            i.putExtra("opt","add");
            startActivity(i);
        }
        else if (id == R.id.nav_mp)
        {
            Intent i = new Intent(ProfileActivity.this,MyPropertyActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_notification)
        {
            Intent i = new Intent(ProfileActivity.this,NotificationActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_share)
        {
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String body="https://play.google.com/store/apps/details?id=com.iteration.grwa";
            i.putExtra(Intent.EXTRA_SUBJECT,body);
            i.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(i,"Share using"));
        }
        else if (id == R.id.nav_rate)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.iteration.grwa"));
            if(!MyStartActivity(i))
            {
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.iteration.grwa"));
                if(!MyStartActivity(i))
                {
                    Log.d("Like","Could not open browser");
                }
            }
        }
        else if (id == R.id.nav_logout)
        {
            session.logoutUser();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean MyStartActivity(Intent i) {
        try
        {
            startActivity(i);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }

    private class GetViewProfile extends AsyncTask<String,Void,String> {

        String status,message,e_id,e_name,ProfilepImg,e_email,e_mobile;

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("UserId",user_id);
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"ViewEmployee.php",joUser.toString());
                JSONObject j = new JSONObject(pdUser);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    message=j.getString("message");
                    JSONArray JsArry=j.getJSONArray("Employee");
                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);

                        e_id =jo.getString("e_id");
                        e_name =jo.getString("e_name");
                        String e_pic =jo.getString("e_pic");
                        ProfilepImg =MainActivity.BASE_URL+e_pic;
                        e_email =jo.getString("e_email");
                        e_mobile =jo.getString("e_mobile");


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
            if(status.equals("1"))
            {
                txtProfileName.setText(e_name);
                txtProfileEmail.setText(e_email);
                txtProfileMobile.setText("+91 "+e_mobile);
                Picasso.with(ProfileActivity.this).load(ProfilepImg).into(ivUserProfilePhoto);
            }
            else
            {
                Toast.makeText(ProfileActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetEditProfileMobile extends AsyncTask<String,Void,String> {

        String status,message;
        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("e_id",user_id);
                joUser.put("e_mobile",txtProfilePhone.getText().toString());
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"editEmploy.php",joUser.toString());
                JSONObject j = new JSONObject(pdUser);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    message=j.getString("message");
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
            if(status.equals("1"))
            {
                dialog.dismiss();
                startActivity(getIntent());
                finish();
            }
            else
            {
                Toast.makeText(ProfileActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
