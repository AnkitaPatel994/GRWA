package com.iteration.grwa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PropertyDetailsActivity extends AppCompatActivity {

    SliderLayout slSlider;
    ArrayList<String> SliderImgArray = new ArrayList<>();
    TextView txtPDPrize,txtPDBHK,txtPDType,txtPDUserName,txtPDUserPhone,txtPDCity,txtPDBuiltArea,txtPDYearBuilt,txtPDBedroom,txtPDBathroom,txtPDPCity,txtPDState,txtPDAddress,txtPDPDescription;
    String imgOne,imgTwo,imgThree;
    ImageView ivImg;
    String usermobile;
    LinearLayout llPDViewCon,llPDSendMessage,llPDPhone;

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
        txtPDBHK.setText(ppbhk);
        String ptname = getIntent().getExtras().getString("ptname");
        txtPDType.setText(ptname);
        String pparea = getIntent().getExtras().getString("pparea");
        txtPDBuiltArea.setText(pparea);
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
        String pdes = getIntent().getExtras().getString("pdes");
        txtPDPDescription.setText(pdes);
        String username = getIntent().getExtras().getString("username");
        txtPDUserName.setText(username);
        String userpic = getIntent().getExtras().getString("userpic");
        String userPicUrl = MainActivity.BASE_URL+userpic;
        Picasso.with(PropertyDetailsActivity.this).load(userPicUrl).into(ivImg);

        String useremail = getIntent().getExtras().getString("useremail");
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
        llPDSendMessage = (LinearLayout)findViewById(R.id.llPDSendMessage);
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
}
