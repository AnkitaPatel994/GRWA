package com.iteration.grwa;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddPropertyActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManager session;
    EditText txtAPId,txtAPPrize,txtAPFBlockno,txtAPFFloor,txtAPBHK,txtAPArea,txtAPYearBuilt,txtAPPropDes,txtAPBedroom,txtAPCity,txtAPState,txtAPBathroom,txtAPAddress;
    LinearLayout llImgOne,llImgTwo,llImgThree,llAPImg,llFlatBox;
    ImageView ivImgOne,ivImgTwo,ivImgThree;
    Spinner spPType;
    ArrayList<String> spListTypeArray=new ArrayList<>();
    ArrayList<String> spListIdTypeArray=new ArrayList<>();
    Button btnAddProp;
    String Prop_Id,Prop_Prize,Prop_BHK,Prop_Type,Prop_Flat_Floor,Prop_Flat_Blockno,Prop_Area,Prop_YearBuilt,Prop_Bedroom,Prop_Bathroom,Prop_Address,Prop_City,Prop_State,Prop_PropDes;
    String flag,typeId,user_id;
    String propId,pid,pprize,ppbhk,ptname,pparea,pyearbuilt,pstate,pcity,paddress,pbedroom,pbathroom,pdes,peid;
    CircleImageView ivUserImg;
    Bitmap bitmap = null;
    String encodedImgpath="";
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    Bitmap bitmapOne = null,bitmapTwo = null,bitmapThree = null;
    String encodedImgOne="",encodedImgTwo="",encodedImgThree="";
    int SELECT_FILE_ONE = 2,SELECT_FILE_TWO = 3,SELECT_FILE_THREE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(1).setChecked(true);

        session = new SessionManager(getApplicationContext());

        HashMap<String,String> user = session.getUserDetails();
        user_id = user.get(SessionManager.user_id);
        String user_name = user.get(SessionManager.user_name);
        String user_email = user.get(SessionManager.user_email);
        String user_pic = user.get(SessionManager.user_pic);

        View headerview = navigationView.getHeaderView(0);
        ivUserImg = (CircleImageView)headerview.findViewById(R.id.ivUserImg);

        GetProfilePic profilePic = new GetProfilePic(AddPropertyActivity.this,user_id,ivUserImg);
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
                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddPropertyActivity.this,
                            Manifest.permission.CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale(AddPropertyActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        selectImage();
                    } else {
                        ActivityCompat.requestPermissions(AddPropertyActivity.this, new String[]{"android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE"}, 200);
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

        GetPTypeList pTypeList = new GetPTypeList();
        pTypeList.execute();

        txtAPId = (EditText)findViewById(R.id.txtAPId);

        llImgOne = (LinearLayout)findViewById(R.id.llImgOne);
        llImgTwo = (LinearLayout)findViewById(R.id.llImgTwo);
        llImgThree = (LinearLayout)findViewById(R.id.llImgThree);

        llAPImg = (LinearLayout)findViewById(R.id.llAPImg);
        llFlatBox = (LinearLayout)findViewById(R.id.llFlatBox);

        ivImgOne = (ImageView) findViewById(R.id.ivImgOne);
        ivImgTwo = (ImageView) findViewById(R.id.ivImgTwo);
        ivImgThree = (ImageView) findViewById(R.id.ivImgThree);

        txtAPPrize = (EditText) findViewById(R.id.txtAPPrize);
        txtAPBHK = (EditText) findViewById(R.id.txtAPBHK);

        spPType = (Spinner) findViewById(R.id.spPType);
        txtAPFFloor = (EditText) findViewById(R.id.txtAPFFloor);
        txtAPFBlockno = (EditText) findViewById(R.id.txtAPFBlockno);

        spPType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int pt = spPType.getSelectedItemPosition();
                typeId = spListIdTypeArray.get(pt);

                if (spPType.getSelectedItem().toString().equals("Flat"))
                {
                    llFlatBox.setVisibility(View.VISIBLE);
                }
                else
                {
                    llFlatBox.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txtAPArea = (EditText) findViewById(R.id.txtAPArea);
        txtAPYearBuilt = (EditText) findViewById(R.id.txtAPYearBuilt);
        txtAPBedroom = (EditText) findViewById(R.id.txtAPBedroom);
        txtAPBathroom = (EditText) findViewById(R.id.txtAPBathroom);
        txtAPAddress = (EditText) findViewById(R.id.txtAPAddress);
        txtAPCity = (EditText) findViewById(R.id.txtAPCity);
        txtAPState = (EditText) findViewById(R.id.txtAPState);
        txtAPPropDes = (EditText) findViewById(R.id.txtAPPropDes);

        btnAddProp = (Button)findViewById(R.id.btnAddProp);

        llImgOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddPropertyActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
                    {
                        ImageFromGallleryOne();
                    }
                    else
                    {
                        ActivityCompat.requestPermissions(AddPropertyActivity.this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 200);
                    }
                } else {
                    ImageFromGallleryOne();
                }
            }

        });

        llImgTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddPropertyActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
                    {
                        ImageFromGallleryTwo();
                    }
                    else
                    {
                        ActivityCompat.requestPermissions(AddPropertyActivity.this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 200);
                    }
                } else {
                    ImageFromGallleryTwo();
                }
            }

        });

        llImgThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddPropertyActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
                    {
                        ImageFromGallleryThree();
                    }
                    else
                    {
                        ActivityCompat.requestPermissions(AddPropertyActivity.this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 200);
                    }
                } else {
                    ImageFromGallleryThree();
                }
            }

        });

        flag = getIntent().getExtras().getString("opt");
        if(flag.equals("add"))
        {
            //Toast.makeText(getApplicationContext(),"add",Toast.LENGTH_SHORT).show();
            llAPImg.setVisibility(View.VISIBLE);
        }
        else if(flag.equals("edit"))
        {
            llAPImg.setVisibility(View.GONE);
            propId = getIntent().getExtras().getString("propId");
            pid = getIntent().getExtras().getString("pid");
            txtAPId.setText(pid);
            pprize = getIntent().getExtras().getString("pprize");
            txtAPPrize.setText(pprize);
            ppbhk = getIntent().getExtras().getString("ppbhk");
            txtAPBHK.setText(ppbhk);
            ptname = getIntent().getExtras().getString("ptname");
            pparea = getIntent().getExtras().getString("pparea");
            txtAPArea.setText(pparea);
            pyearbuilt = getIntent().getExtras().getString("pyearbuilt");
            txtAPYearBuilt.setText(pyearbuilt);
            pstate = getIntent().getExtras().getString("pstate");
            txtAPState.setText(pstate);
            pcity = getIntent().getExtras().getString("pcity");
            txtAPCity.setText(pcity);
            paddress = getIntent().getExtras().getString("paddress");
            txtAPAddress.setText(paddress);
            pbedroom = getIntent().getExtras().getString("pbedroom");
            txtAPBedroom.setText(pbedroom);
            pbathroom = getIntent().getExtras().getString("pbathroom");
            txtAPBathroom.setText(pbathroom);
            pdes = getIntent().getExtras().getString("pdes");
            txtAPPropDes.setText(pdes);
            peid = getIntent().getExtras().getString("peid");

        }

        btnAddProp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtAPPrize.getText().toString().equals("") && txtAPBHK.getText().toString().equals("") && txtAPArea.getText().toString().equals("") && txtAPYearBuilt.getText().toString().equals("") && txtAPBedroom.getText().toString().equals("") && txtAPBathroom.getText().toString().equals("") && txtAPAddress.getText().toString().equals("") && txtAPCity.getText().toString().equals("") && txtAPState.getText().toString().equals("") && txtAPPropDes.getText().toString().equals(""))
                {
                    Toast.makeText(AddPropertyActivity.this,"Enter Prize.",Toast.LENGTH_SHORT).show();
                }
                else if(txtAPBHK.getText().toString().equals("") && txtAPArea.getText().toString().equals("") && txtAPYearBuilt.getText().toString().equals("") && txtAPBedroom.getText().toString().equals("") && txtAPBathroom.getText().toString().equals("") && txtAPAddress.getText().toString().equals("") && txtAPCity.getText().toString().equals("") && txtAPState.getText().toString().equals("") && txtAPPropDes.getText().toString().equals(""))
                {
                    Toast.makeText(AddPropertyActivity.this,"Enter BHK.",Toast.LENGTH_SHORT).show();
                }
                else if(txtAPArea.getText().toString().equals("") && txtAPYearBuilt.getText().toString().equals("") && txtAPBedroom.getText().toString().equals("") && txtAPBathroom.getText().toString().equals("") && txtAPAddress.getText().toString().equals("") && txtAPCity.getText().toString().equals("") && txtAPState.getText().toString().equals("") && txtAPPropDes.getText().toString().equals(""))
                {
                    Toast.makeText(AddPropertyActivity.this,"Enter Built-up Area",Toast.LENGTH_SHORT).show();
                }
                else if(txtAPYearBuilt.getText().toString().equals("") && txtAPBedroom.getText().toString().equals("") && txtAPBathroom.getText().toString().equals("") && txtAPAddress.getText().toString().equals("") && txtAPCity.getText().toString().equals("") && txtAPState.getText().toString().equals("") && txtAPPropDes.getText().toString().equals(""))
                {
                    Toast.makeText(AddPropertyActivity.this,"Enter Year Built",Toast.LENGTH_SHORT).show();
                }
                else if(txtAPBedroom.getText().toString().equals("") && txtAPBathroom.getText().toString().equals("") && txtAPAddress.getText().toString().equals("") && txtAPCity.getText().toString().equals("") && txtAPState.getText().toString().equals("") && txtAPPropDes.getText().toString().equals(""))
                {
                    Toast.makeText(AddPropertyActivity.this,"Enter Number of Bedroom",Toast.LENGTH_SHORT).show();
                }
                else if(txtAPBathroom.getText().toString().equals("") && txtAPAddress.getText().toString().equals("") && txtAPCity.getText().toString().equals("") && txtAPState.getText().toString().equals("") && txtAPPropDes.getText().toString().equals(""))
                {
                    Toast.makeText(AddPropertyActivity.this,"Enter Number of Bathroom",Toast.LENGTH_SHORT).show();
                }
                else if(txtAPAddress.getText().toString().equals("") && txtAPCity.getText().toString().equals("") && txtAPState.getText().toString().equals("") && txtAPPropDes.getText().toString().equals(""))
                {
                    Toast.makeText(AddPropertyActivity.this,"Enter Address",Toast.LENGTH_SHORT).show();
                }
                else if(txtAPCity.getText().toString().equals("") && txtAPState.getText().toString().equals("") && txtAPPropDes.getText().toString().equals(""))
                {
                    Toast.makeText(AddPropertyActivity.this,"Enter City",Toast.LENGTH_SHORT).show();
                }
                else if(txtAPState.getText().toString().equals("") && txtAPPropDes.getText().toString().equals(""))
                {
                    Toast.makeText(AddPropertyActivity.this,"Enter State",Toast.LENGTH_SHORT).show();
                }
                else if(txtAPPropDes.getText().toString().equals(""))
                {
                    Toast.makeText(AddPropertyActivity.this,"Enter Property Description",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Prop_Id = txtAPId.getText().toString();
                    Prop_Prize = txtAPPrize.getText().toString();
                    Prop_BHK = txtAPBHK.getText().toString();
                    Prop_Type = typeId;
                    Prop_Area = txtAPArea.getText().toString();
                    Prop_YearBuilt = txtAPYearBuilt.getText().toString();
                    Prop_Bedroom = txtAPBedroom.getText().toString();
                    Prop_Bathroom = txtAPBathroom.getText().toString();
                    Prop_Address = txtAPAddress.getText().toString();
                    Prop_City = txtAPCity.getText().toString();
                    Prop_State = txtAPState.getText().toString();
                    Prop_PropDes = txtAPPropDes.getText().toString();

                    if (spPType.getSelectedItem().toString().equals("Flat"))
                    {
                        if(txtAPFFloor.getText().toString().equals("") && txtAPFBlockno.getText().toString().equals(""))
                        {
                            Toast.makeText(AddPropertyActivity.this,"Enter Floor",Toast.LENGTH_SHORT).show();
                        }
                        else if(txtAPFBlockno.getText().toString().equals(""))
                        {
                            Toast.makeText(AddPropertyActivity.this,"Enter Floor Block no",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Prop_Flat_Floor = txtAPFFloor.getText().toString();
                            Prop_Flat_Blockno = txtAPFBlockno.getText().toString();

                            if(flag.equals("add"))
                            {
                                GetInsertProperty insertProperty = new GetInsertProperty();
                                insertProperty.execute();
                            }
                            else if(flag.equals("edit"))
                            {
                                GetEditProperty editProperty = new GetEditProperty();
                                editProperty.execute();
                            }
                        }
                    }
                    else
                    {
                        Prop_Flat_Floor = "";
                        Prop_Flat_Blockno = "";

                        if(flag.equals("add"))
                        {
                            GetInsertProperty insertProperty = new GetInsertProperty();
                            insertProperty.execute();
                        }
                        else if(flag.equals("edit"))
                        {
                            GetEditProperty editProperty = new GetEditProperty();
                            editProperty.execute();
                        }
                    }


                }
            }
        });
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPropertyActivity.this);
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

    private void ImageFromGallleryOne() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_ONE);
    }

    private void ImageFromGallleryTwo() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_TWO);
    }

    private void ImageFromGallleryThree() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_THREE);
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

                GetSendImg sendImg = new GetSendImg(AddPropertyActivity.this,user_id,encodedImgpath);
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

                GetSendImg sendImg = new GetSendImg(AddPropertyActivity.this,user_id,encodedImgpath);
                sendImg.execute();

            }
            else if (requestCode == SELECT_FILE_ONE)
            {
                Uri uri = data.getData();
                try {
                    bitmapOne = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmapOne.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                byte[] b = bytes.toByteArray();
                encodedImgOne = Base64.encodeToString(b, Base64.DEFAULT);

                ivImgOne.setImageBitmap(bitmapOne);

            }
            else if (requestCode == SELECT_FILE_TWO)
            {
                Uri uri = data.getData();
                try {
                    bitmapTwo = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmapTwo.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                byte[] b = bytes.toByteArray();
                encodedImgTwo = Base64.encodeToString(b, Base64.DEFAULT);

                ivImgTwo.setImageBitmap(bitmapTwo);

            }
            else if (requestCode == SELECT_FILE_THREE)
            {
                Uri uri = data.getData();
                try {
                    bitmapThree = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmapThree.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                byte[] b = bytes.toByteArray();
                encodedImgThree = Base64.encodeToString(b, Base64.DEFAULT);

                ivImgThree.setImageBitmap(bitmapThree);

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            Intent i = new Intent(AddPropertyActivity.this,HomeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_mp)
        {
            Intent i = new Intent(AddPropertyActivity.this,MyPropertyActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_profile)
        {
            Intent i = new Intent(AddPropertyActivity.this,ProfileActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_notification)
        {
            Intent i = new Intent(AddPropertyActivity.this,NotificationActivity.class);
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

    private class GetEditProperty extends AsyncTask<String,Void,String> {

        String status,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(AddPropertyActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(true);
            dialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            JSONObject joUser=new JSONObject();
            try {

                joUser.put("p_id",propId);
                joUser.put("p_pid",Prop_Id);
                joUser.put("p_prize",Prop_Prize);
                joUser.put("p_bhk",Prop_BHK);
                joUser.put("p_block_no",Prop_Flat_Blockno);
                joUser.put("p_area",Prop_Area);
                joUser.put("p_yearbuilt",Prop_YearBuilt);
                joUser.put("p_bedroom",Prop_Bedroom);
                joUser.put("p_bathroom",Prop_Bathroom);
                joUser.put("p_address",Prop_Address);
                joUser.put("p_city",Prop_City);
                joUser.put("p_state",Prop_State);
                joUser.put("p_pdes",Prop_PropDes);
                joUser.put("p_e_id",peid);

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"EditProperty.php",joUser.toString());
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
            dialog.dismiss();
            if(status.equals("1"))
            {
                Intent i = new Intent(getApplicationContext(),MyPropertyActivity.class);
                startActivity(i);
                finish();
                //Toast.makeText(AddPropertyActivity.this,message,Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(AddPropertyActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetPTypeList extends AsyncTask<String,Void,String> {

        String status,message;

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"PropertyType.php",joUser.toString());
                JSONObject j = new JSONObject(pdUser);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    message=j.getString("message");
                    JSONArray JsArry=j.getJSONArray("PropertyType");
                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);


                        String id =jo.getString("id");
                        String PropType =jo.getString("type");

                        spListIdTypeArray.add(id);
                        spListTypeArray.add(PropType);
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
                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, spListTypeArray);
                spPType.setAdapter(spinnerArrayAdapter);
            }
            else
            {
                Toast.makeText(AddPropertyActivity.this,message,Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class GetInsertProperty extends AsyncTask<String,Void,String> {

        String status,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(AddPropertyActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(true);
            dialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                joUser.put("p_pimg_one",encodedImgOne);
                joUser.put("p_pimg_two",encodedImgTwo);
                joUser.put("p_pimg_three",encodedImgThree);
                joUser.put("p_prize",Prop_Prize);
                joUser.put("p_bhk",Prop_BHK);
                joUser.put("p_type_id",Prop_Type);
                joUser.put("p_floor",Prop_Flat_Floor);
                joUser.put("p_block_no",Prop_Flat_Blockno);
                joUser.put("p_area",Prop_Area);
                joUser.put("p_yearbuilt",Prop_YearBuilt);
                joUser.put("p_bedroom",Prop_Bedroom);
                joUser.put("p_bathroom",Prop_Bathroom);
                joUser.put("p_address",Prop_Address);
                joUser.put("p_city",Prop_City);
                joUser.put("p_state",Prop_State);
                joUser.put("p_pdes",Prop_PropDes);
                joUser.put("p_e_id",user_id);

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"InsertProperty.php",joUser.toString());
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
            dialog.dismiss();
            if(status.equals("1"))
            {
                Intent i = new Intent(getApplicationContext(),MyPropertyActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(AddPropertyActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }

    }
}