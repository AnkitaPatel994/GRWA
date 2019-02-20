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
    EditText txtAPId,txtAPPrize,txtAPBHK,txtAPArea,txtAPYearBuilt,txtAPPropDes,txtAPBedroom,txtAPCity,txtAPState,txtAPBathroom,txtAPAddress;
    LinearLayout llImgOne,llImgTwo,llImgThree;
    ImageView ivImgOne,ivImgTwo,ivImgThree;
    Spinner spPType;
    ArrayList<String> spListTypeArray=new ArrayList<>();
    ArrayList<String> spListIdTypeArray=new ArrayList<>();
    Button btnAddProp;
    String Prop_Id,Prop_Prize,Prop_BHK,Prop_Type,Prop_Area,Prop_YearBuilt,Prop_Bedroom,Prop_Bathroom,Prop_Address,Prop_City,Prop_State,Prop_PropDes;
    Bitmap bitmap = null;
    String str_imgpath,encodedImgpathOne,encodedImgpathTwo,encodedImgpathThree;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    String flag,typeId;
    String propId,pid,pprize,ppbhk,ptname,pparea,pyearbuilt,pstate,pcity,paddress,pbedroom,pbathroom,pdes,peid,pimgOne,pimgTwo,pimgThree;

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

        session = new SessionManager(getApplicationContext());

        HashMap<String,String> user = session.getUserDetails();
        String user_name = user.get(SessionManager.user_name);
        String user_email = user.get(SessionManager.user_email);
        String user_pic = user.get(SessionManager.user_pic);
        String url_user_pic = MainActivity.BASE_URL+user_pic;

        View headerview = navigationView.getHeaderView(0);
        CircleImageView ivUserImg = (CircleImageView)headerview.findViewById(R.id.ivUserImg);
        Picasso.with(AddPropertyActivity.this).load(url_user_pic).into(ivUserImg);

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

        ivImgOne = (ImageView) findViewById(R.id.ivImgOne);
        ivImgTwo = (ImageView) findViewById(R.id.ivImgTwo);
        ivImgThree = (ImageView) findViewById(R.id.ivImgThree);

        txtAPPrize = (EditText) findViewById(R.id.txtAPPrize);
        txtAPBHK = (EditText) findViewById(R.id.txtAPBHK);

        spPType = (Spinner) findViewById(R.id.spPType);
        spPType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pt = spPType.getSelectedItemPosition();
                typeId = spListIdTypeArray.get(pt);

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
                if (ContextCompat.checkSelfPermission(AddPropertyActivity.this,

                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(AddPropertyActivity.this,
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

        llImgTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddPropertyActivity.this,

                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(AddPropertyActivity.this,
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

        llImgThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddPropertyActivity.this,

                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(AddPropertyActivity.this,
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

        flag = getIntent().getExtras().getString("opt");
        if(flag.equals("add"))
        {
            Toast.makeText(getApplicationContext(),"add",Toast.LENGTH_SHORT).show();
        }
        else if(flag.equals("edit"))
        {
            Toast.makeText(getApplicationContext(),"edit",Toast.LENGTH_SHORT).show();
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
            pimgOne = getIntent().getExtras().getString("pimgOne");
            Picasso.with(AddPropertyActivity.this).load(pimgOne).into(ivImgOne);
            pimgTwo = getIntent().getExtras().getString("pimgTwo");
            Picasso.with(AddPropertyActivity.this).load(pimgTwo).into(ivImgTwo);
            pimgThree = getIntent().getExtras().getString("pimgThree");
            Picasso.with(getApplicationContext()).load(pimgThree).into(ivImgThree);
        }

        btnAddProp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                if(flag.equals("add"))
                {
                    Toast.makeText(getApplicationContext(),"add",Toast.LENGTH_SHORT).show();
                }
                else if(flag.equals("edit"))
                {
                    GetEditProperty editProperty = new GetEditProperty();
                    editProperty.execute();
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
                File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Uri tempUri = getImageUri(AddPropertyActivity.this, bitmap);
                str_imgpath = getRealPathFromURI(tempUri);

                byte[] b = bytes.toByteArray();
                encodedImgpathOne = Base64.encodeToString(b, Base64.DEFAULT);

                ivImgOne.setImageBitmap(bitmap);
            }
            else if (requestCode == SELECT_FILE)
            {
                Uri selectedImageUri = data.getData();

                InputStream in = null;
                try {
                    final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
                    in = AddPropertyActivity.this.getContentResolver().openInputStream(selectedImageUri);

                    // Decode image size
                    BitmapFactory.Options o = new BitmapFactory.Options();
                    o.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(in, null, o);
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int scale = 1;
                    while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                            IMAGE_MAX_SIZE) {
                        scale++;

                    }
                    in = AddPropertyActivity.this.getContentResolver().openInputStream(selectedImageUri);
                    if (scale > 1) {
                        scale--;

                        o = new BitmapFactory.Options();
                        o.inSampleSize = scale;
                        bitmap = BitmapFactory.decodeStream(in, null, o);

                        // resize to desired dimensions
                        int height = bitmap.getHeight();
                        int width = bitmap.getWidth();

                        double y = Math.sqrt(IMAGE_MAX_SIZE
                                / (((double) width) / height));
                        double x = (y / height) * width;

                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) x,
                                (int) y, true);
                        bitmap.recycle();
                        bitmap = scaledBitmap;

                        System.gc();
                    } else {
                        bitmap = BitmapFactory.decodeStream(in);
                    }
                    in.close();

                } catch (Exception ignored) {

                }

                Uri tempUri = getImageUri(AddPropertyActivity.this, bitmap);
                str_imgpath = getRealPathFromURI(tempUri);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                byte[] b = bytes.toByteArray();
                encodedImgpathOne = Base64.encodeToString(b, Base64.DEFAULT);

                ivImgOne.setImageBitmap(bitmap);

            }
        }
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri uri) {
        Cursor cursor = AddPropertyActivity.this.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
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
                joUser.put("p_type_id",Prop_Type);
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
}
