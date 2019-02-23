package com.iteration.grwa;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewAllPropertyActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rvPropertyList;
    String typeId;
    ArrayList<HashMap<String,String>> PropertiesListArray = new ArrayList<>();
    SessionManager session;
    ImageView ivList,ivGrid;
    CircleImageView ivUserImg;
    Bitmap bitmap = null;
    String encodedImgpath="",user_id;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_property);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        session = new SessionManager(getApplicationContext());
        HashMap<String,String> user = session.getUserDetails();
        String user_name = user.get(SessionManager.user_name);
        String user_email = user.get(SessionManager.user_email);
        user_id = user.get(SessionManager.user_id);

        View headerview = navigationView.getHeaderView(0);
        ivUserImg = (CircleImageView)headerview.findViewById(R.id.ivUserImg);

        GetProfilePic profilePic = new GetProfilePic(ViewAllPropertyActivity.this,user_id,ivUserImg);
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
                    if (ActivityCompat.shouldShowRequestPermissionRationale(ViewAllPropertyActivity.this,
                            Manifest.permission.CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale(ViewAllPropertyActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        selectImage();
                    } else {
                        ActivityCompat.requestPermissions(ViewAllPropertyActivity.this, new String[]{"android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE"}, 200);
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

            /*================== Property List view ==================*/

        rvPropertyList = (RecyclerView)findViewById(R.id.rvPropertyList);
        rvPropertyList.setHasFixedSize(true);

        ivList = (ImageView)findViewById(R.id.ivList);
        ivGrid = (ImageView)findViewById(R.id.ivGrid);

        ivGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivList.setVisibility(View.VISIBLE);
                ivGrid.setVisibility(View.GONE);
                RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),2);
                rvPropertyList.setLayoutManager(manager);

            }
        });

        ivList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ivGrid.setVisibility(View.VISIBLE);
                ivList.setVisibility(View.GONE);
                RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
                rvPropertyList.setLayoutManager(manager);

            }
        });

        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
        rvPropertyList.setLayoutManager(manager);

        typeId = getIntent().getExtras().getString("id");

        GetPropertyList propertyList = new GetPropertyList();
        propertyList.execute();


    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewAllPropertyActivity.this);
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

                GetSendImg sendImg = new GetSendImg(ViewAllPropertyActivity.this,user_id,encodedImgpath);
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

                GetSendImg sendImg = new GetSendImg(ViewAllPropertyActivity.this,user_id,encodedImgpath);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_all_property, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_search) {
            Intent i =new Intent(getApplicationContext(),SearchActivity.class);
            i.putExtra("typeId",typeId);
            startActivity(i);
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
            Intent i = new Intent(ViewAllPropertyActivity.this,HomeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_ap)
        {
            Intent i = new Intent(ViewAllPropertyActivity.this,AddPropertyActivity.class);
            i.putExtra("opt","add");
            startActivity(i);
        }
        else if (id == R.id.nav_mp)
        {
            Intent i = new Intent(ViewAllPropertyActivity.this,MyPropertyActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_profile)
        {
            Intent i = new Intent(ViewAllPropertyActivity.this,ProfileActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_notification)
        {
            Intent i = new Intent(ViewAllPropertyActivity.this,NotificationActivity.class);
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

    private class GetPropertyList extends AsyncTask<String,Void,String> {
        String status,message;
        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("PropertyType",typeId);
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"Properties.php",joUser.toString());
                JSONObject j = new JSONObject(pdUser);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    message=j.getString("message");
                    JSONArray JsArry=j.getJSONArray("Properties");
                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);

                        HashMap<String,String > hashMap = new HashMap<>();

                        String id =jo.getString("id");
                        String pid =jo.getString("pid");
                        String pimgone =jo.getString("pimgone");
                        String pimgtwo =jo.getString("pimgtwo");
                        String pimgthree =jo.getString("pimgthree");
                        String pprize =jo.getString("pprize");
                        String ppbhk =jo.getString("ppbhk");
                        String ptname =jo.getString("ptname");
                        String pparea =jo.getString("pparea");
                        String pyearbuilt =jo.getString("pyearbuilt");
                        String pstate =jo.getString("pstate");
                        String pcity =jo.getString("pcity");
                        String paddress =jo.getString("paddress");
                        String pbedroom =jo.getString("pbedroom");
                        String pbathroom =jo.getString("pbathroom");
                        String pdes =jo.getString("pdes");
                        String eid =jo.getString("userid");
                        String username =jo.getString("username");
                        String userpic =jo.getString("userpic");
                        String useremail =jo.getString("useremail");
                        String usermobile =jo.getString("usermobile");

                        hashMap.put("id",id);
                        hashMap.put("pid",pid);
                        hashMap.put("pimgone",pimgone);
                        hashMap.put("pimgtwo",pimgtwo);
                        hashMap.put("pimgthree",pimgthree);
                        hashMap.put("pprize",pprize);
                        hashMap.put("ppbhk",ppbhk);
                        hashMap.put("ptname",ptname);
                        hashMap.put("pparea",pparea);
                        hashMap.put("pyearbuilt",pyearbuilt);
                        hashMap.put("pstate",pstate);
                        hashMap.put("pcity",pcity);
                        hashMap.put("paddress",paddress);
                        hashMap.put("pbedroom",pbedroom);
                        hashMap.put("pbathroom",pbathroom);
                        hashMap.put("pdes",pdes);
                        hashMap.put("eid",eid);
                        hashMap.put("username",username);
                        hashMap.put("userpic",userpic);
                        hashMap.put("useremail",useremail);
                        hashMap.put("usermobile",usermobile);

                        PropertiesListArray.add(hashMap);
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
                ListpropertyAdapter listpropertyAdapter = new ListpropertyAdapter(ViewAllPropertyActivity.this,PropertiesListArray);
                rvPropertyList.setAdapter(listpropertyAdapter);
            }
            else
            {
                Toast.makeText(ViewAllPropertyActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

}
