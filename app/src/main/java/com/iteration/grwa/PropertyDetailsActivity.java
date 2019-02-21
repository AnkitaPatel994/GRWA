package com.iteration.grwa;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PropertyDetailsActivity extends AppCompatActivity {

    SliderLayout slSlider;
    ArrayList<String> SliderImgArray = new ArrayList<>();
    TextView txtPDPrize,txtPDBHK,txtPDType,txtPDUserName,txtPDUserPhone,txtPDCity,txtPDBuiltArea,txtPDYearBuilt,txtPDBedroom,txtPDBathroom,txtPDPCity,txtPDState,txtPDAddress,txtPDPDescription;
    String imgOne,imgTwo,imgThree;
    ImageView ivImg;
    String userPicUrl,usermobile,useremail,username,pdes;
    LinearLayout llPDViewCon,llPDSendMessage,llPDPhone;
    Dialog dialog,dialog_rm;
    EditText txtPDIName,txtPDIPhone,txtPDIEmail,txtPDIMessage;
    Button btnInquire;
    TextView txtReadMore;
    Bitmap bitmapLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        slSlider = (SliderLayout)findViewById(R.id.slSlider);

        txtPDPrize = (TextView)findViewById(R.id.txtPDPrize);
        txtPDBHK = (TextView)findViewById(R.id.txtPDBHK);
        txtPDType = (TextView)findViewById(R.id.txtPDType);
        txtPDCity = (TextView)findViewById(R.id.txtPDCity);
        txtPDBuiltArea = (TextView)findViewById(R.id.txtPDBuiltArea);
        txtPDYearBuilt = (TextView)findViewById(R.id.txtPDYearBuilt);
        txtPDBedroom = (TextView)findViewById(R.id.txtPDBedroom);
        txtPDBathroom = (TextView)findViewById(R.id.txtPDBathroom);
        txtPDPCity = (TextView)findViewById(R.id.txtPDPCity);
        txtPDState = (TextView)findViewById(R.id.txtPDState);
        txtPDAddress = (TextView)findViewById(R.id.txtPDAddress);
        txtPDPDescription = (TextView)findViewById(R.id.txtPDPDescription);
        txtPDUserName = (TextView)findViewById(R.id.txtPDUserName);
        txtPDUserPhone = (TextView)findViewById(R.id.txtPDUserPhone);
        ivImg = (ImageView) findViewById(R.id.ivImg);

        String propId = getIntent().getExtras().getString("id");
        String pid = getIntent().getExtras().getString("pid");
        String pprize = getIntent().getExtras().getString("pprize");
        txtPDPrize.setText(pprize);
        String ppbhk = getIntent().getExtras().getString("ppbhk");
        txtPDBHK.setText(ppbhk+" BHK");
        String ptname = getIntent().getExtras().getString("ptname");
        txtPDType.setText(ptname);
        String pparea = getIntent().getExtras().getString("pparea");
        txtPDBuiltArea.setText(pparea+" Sq.Ft");
        String pyearbuilt = getIntent().getExtras().getString("pyearbuilt");
        txtPDYearBuilt.setText(pyearbuilt);
        String pstate = getIntent().getExtras().getString("pstate");
        txtPDState.setText(pstate);
        String pcity = getIntent().getExtras().getString("pcity");
        txtPDCity.setText(pcity);
        txtPDPCity.setText(pcity);
        String paddress = getIntent().getExtras().getString("paddress");
        txtPDAddress.setText(paddress);
        String pbedroom = getIntent().getExtras().getString("pbedroom");
        txtPDBedroom.setText(pbedroom);
        String pbathroom = getIntent().getExtras().getString("pbathroom");
        txtPDBathroom.setText(pbathroom);
        pdes = getIntent().getExtras().getString("pdes");
        txtPDPDescription.setText(pdes);
        username = getIntent().getExtras().getString("username");
        txtPDUserName.setText(username);
        String userpic = getIntent().getExtras().getString("userpic");
        userPicUrl = MainActivity.BASE_URL+userpic;
        Picasso.with(PropertyDetailsActivity.this).load(userPicUrl).into(ivImg);

        useremail = getIntent().getExtras().getString("useremail");
        usermobile = getIntent().getExtras().getString("usermobile");
        txtPDUserPhone.setText(usermobile);

        String pimgOne = getIntent().getExtras().getString("pimgone");
        String pimgTwo = getIntent().getExtras().getString("pimgtwo");
        String pimgThree = getIntent().getExtras().getString("pimgthree");

        imgOne = MainActivity.BASE_URL+pimgOne;
        imgTwo = MainActivity.BASE_URL+pimgTwo;
        imgThree = MainActivity.BASE_URL+pimgThree;

        GetSliderImg sliderImg = new GetSliderImg();
        sliderImg.execute();

        llPDViewCon = (LinearLayout)findViewById(R.id.llPDViewCon);
        llPDViewCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(PropertyDetailsActivity.this,android.R.style.Theme_Light_NoTitleBar);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.contact_dialog);
                dialog.setCancelable(true);

                TextView txtDName=(TextView) dialog.findViewById(R.id.txtDName);
                txtDName.setText(username);
                TextView txtDEmail=(TextView) dialog.findViewById(R.id.txtDEmail);
                txtDEmail.setText(useremail);
                TextView txtDPhone=(TextView) dialog.findViewById(R.id.txtDPhone);
                txtDPhone.setText(usermobile);
                ImageView ivDImg=(ImageView)dialog.findViewById(R.id.ivDImg);
                Picasso.with(PropertyDetailsActivity.this).load(userPicUrl).into(ivDImg);

                LinearLayout llContactDialog=(LinearLayout) dialog.findViewById(R.id.llContactDialog);
                llContactDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        llPDSendMessage = (LinearLayout)findViewById(R.id.llPDSendMessage);
        llPDSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "sms:" + usermobile));
                intent.putExtra( "sms_body", "" );
                startActivity(intent);
            }
        });

        llPDPhone = (LinearLayout)findViewById(R.id.llPDPhone);
        llPDPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PropertyDetailsActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(PropertyDetailsActivity.this,
                            Manifest.permission.CALL_PHONE)) {

                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + usermobile));
                        startActivity(callIntent);

                    } else {
                        ActivityCompat.requestPermissions(PropertyDetailsActivity.this, new String[]{"android.permission.CALL_PHONE",}, 200);
                    }
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + usermobile));
                    startActivity(callIntent);
                }
            }
        });

        txtPDIName = (EditText)findViewById(R.id.txtPDIName);
        txtPDIPhone = (EditText)findViewById(R.id.txtPDIPhone);
        txtPDIEmail = (EditText)findViewById(R.id.txtPDIEmail);
        txtPDIMessage = (EditText)findViewById(R.id.txtPDIMessage);
        btnInquire = (Button)findViewById(R.id.btnInquire);

        btnInquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtPDIName.getText().toString().equals("") && txtPDIPhone.getText().toString().equals("") && txtPDIEmail.getText().toString().equals("") && txtPDIMessage.getText().toString().equals(""))
                {
                    Toast.makeText(PropertyDetailsActivity.this,"Enter Your Name.",Toast.LENGTH_SHORT).show();
                }
                else if (txtPDIPhone.getText().toString().equals("") && txtPDIEmail.getText().toString().equals("") && txtPDIMessage.getText().toString().equals(""))
                {
                    Toast.makeText(PropertyDetailsActivity.this,"Enter Your Mobile.",Toast.LENGTH_SHORT).show();
                }
                else if (txtPDIEmail.getText().toString().equals("") && txtPDIMessage.getText().toString().equals(""))
                {
                    Toast.makeText(PropertyDetailsActivity.this,"Enter Your Email.",Toast.LENGTH_SHORT).show();
                }
                else if (txtPDIMessage.getText().toString().equals(""))
                {
                    Toast.makeText(PropertyDetailsActivity.this,"Enter Your Message.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    GetInsertInquire insertInquire = new GetInsertInquire();
                    insertInquire.execute();
                }

            }
        });

        txtReadMore = (TextView)findViewById(R.id.txtReadMore);
        txtReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_rm = new Dialog(PropertyDetailsActivity.this,android.R.style.Theme_Light_NoTitleBar);
                dialog_rm.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog_rm.setContentView(R.layout.readmore_dialog);
                dialog_rm.setCancelable(true);

                TextView txtRDescription = (TextView)dialog_rm.findViewById(R.id.txtRDescription);
                txtRDescription.setText(pdes);
                ImageView ivRDClose = (ImageView) dialog_rm.findViewById(R.id.ivRDClose);
                ivRDClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_rm.dismiss();
                    }
                });
                dialog_rm.show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pdshare, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home)
            finish();

        if (id == R.id.menu_share)
        {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            share.putExtra(Intent.EXTRA_STREAM, bitmapLayout);
            startActivity(Intent.createChooser(share, "Share Image"));
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetSliderImg extends AsyncTask<String,Void,String> implements BaseSliderView.OnSliderClickListener {
        @Override
        protected String doInBackground(String... strings) {

            SliderImgArray.add(imgOne);
            SliderImgArray.add(imgTwo);
            SliderImgArray.add(imgThree);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            for (String name : SliderImgArray) {
                DefaultSliderView textSliderView = new DefaultSliderView(PropertyDetailsActivity.this);
                // initialize a SliderLayout
                textSliderView
                        .image(name)
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                slSlider.addSlider(textSliderView);
            }
            slSlider.setCustomAnimation(new DescriptionAnimation());
            slSlider.setDuration(5000);

        }

        @Override
        public void onSliderClick(BaseSliderView slider) {
            slSlider.startAutoCycle();
        }
    }

    private class GetInsertInquire extends AsyncTask<String,Void,String> {

        String status,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(PropertyDetailsActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(true);
            dialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            JSONObject joUser=new JSONObject();
            try {

                joUser.put("i_name",txtPDIName.getText().toString());
                joUser.put("i_phone",txtPDIPhone.getText().toString());
                joUser.put("i_email",txtPDIEmail.getText().toString());
                joUser.put("i_message",txtPDIMessage.getText().toString());

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"insertInquiry.php",joUser.toString());
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
                txtPDIName.setText("");
                txtPDIPhone.setText("");
                txtPDIEmail.setText("");
                txtPDIMessage.setText("");
                Toast.makeText(PropertyDetailsActivity.this,"Submit",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(PropertyDetailsActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
