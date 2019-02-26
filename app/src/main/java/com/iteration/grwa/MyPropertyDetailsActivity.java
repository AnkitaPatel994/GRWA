package com.iteration.grwa;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import java.util.HashMap;

public class MyPropertyDetailsActivity extends AppCompatActivity {

    SliderLayout slMySlider;
    ArrayList<String> MySliderImgArray = new ArrayList<>();
    String imgOne,imgTwo,imgThree;
    SessionManager session;
    Dialog dialog;
    String propId,pid,pprize,ppbhk,ptname,pparea,pyearbuilt,pstate,pcity,paddress,pbedroom,pbathroom,pdes,user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_property_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        slMySlider = (SliderLayout)findViewById(R.id.slMySlider);

        TextView txtMyPDPrize = (TextView)findViewById(R.id.txtMyPDPrize);
        TextView txtMyPDBHK = (TextView)findViewById(R.id.txtMyPDBHK);
        TextView txtMyPDType = (TextView)findViewById(R.id.txtMyPDType);
        TextView txtMyPDCity = (TextView)findViewById(R.id.txtMyPDCity);
        TextView txtMyPDBuiltArea = (TextView)findViewById(R.id.txtMyPDBuiltArea);
        TextView txtMyPDYearBuilt = (TextView)findViewById(R.id.txtMyPDYearBuilt);
        TextView txtMyPDBedroom = (TextView)findViewById(R.id.txtMyPDBedroom);
        TextView txtMyPDBathroom = (TextView)findViewById(R.id.txtMyPDBathroom);
        TextView txtMyPDPCity = (TextView)findViewById(R.id.txtMyPDPCity);
        TextView txtMyPDState = (TextView)findViewById(R.id.txtMyPDState);
        TextView txtMyPDAddress = (TextView)findViewById(R.id.txtMyPDAddress);
        TextView txtMyPDPDescription = (TextView)findViewById(R.id.txtMyPDPDescription);
        TextView txtMyPDUserName = (TextView)findViewById(R.id.txtMyPDUserName);
        TextView txtMyPDUserPhone = (TextView)findViewById(R.id.txtMyPDUserPhone);
        ImageView ivMyImg = (ImageView) findViewById(R.id.ivMyImg);

        session = new SessionManager(getApplicationContext());
        HashMap<String,String> user = session.getUserDetails();
        user_id = user.get(SessionManager.user_id);

        propId = getIntent().getExtras().getString("id");
        pid = getIntent().getExtras().getString("pid");
        pprize = getIntent().getExtras().getString("pprize");
        txtMyPDPrize.setText(pprize);
        ppbhk = getIntent().getExtras().getString("ppbhk");
        txtMyPDBHK.setText(ppbhk);
        ptname = getIntent().getExtras().getString("ptname");
        txtMyPDType.setText(ptname);
        pparea = getIntent().getExtras().getString("pparea");
        txtMyPDBuiltArea.setText(pparea);
        pyearbuilt = getIntent().getExtras().getString("pyearbuilt");
        txtMyPDYearBuilt.setText(pyearbuilt);
        pstate = getIntent().getExtras().getString("pstate");
        txtMyPDState.setText(pstate);
        pcity = getIntent().getExtras().getString("pcity");
        txtMyPDCity.setText(pcity);
        txtMyPDPCity.setText(pcity);
        paddress = getIntent().getExtras().getString("paddress");
        txtMyPDAddress.setText(paddress);
        pbedroom = getIntent().getExtras().getString("pbedroom");
        txtMyPDBedroom.setText(pbedroom);
        pbathroom = getIntent().getExtras().getString("pbathroom");
        txtMyPDBathroom.setText(pbathroom);
        pdes = getIntent().getExtras().getString("pdes");
        txtMyPDPDescription.setText(pdes);
        String username = getIntent().getExtras().getString("username");
        txtMyPDUserName.setText(username);
        String userpic = getIntent().getExtras().getString("userpic");
        String userPicUrl = MainActivity.BASE_URL+userpic;
        Picasso.with(MyPropertyDetailsActivity.this).load(userPicUrl).into(ivMyImg);

        TextView txtMyPDDate = (TextView)findViewById(R.id.txtMyPDDate);
        String pdate = getIntent().getExtras().getString("pdate");
        txtMyPDDate.setText(pdate);

        String useremail = getIntent().getExtras().getString("useremail");
        String usermobile = getIntent().getExtras().getString("usermobile");
        txtMyPDUserPhone.setText(usermobile);

        String pimgOne = getIntent().getExtras().getString("pimgone");
        String pimgTwo = getIntent().getExtras().getString("pimgtwo");
        String pimgThree = getIntent().getExtras().getString("pimgthree");

        imgOne = MainActivity.BASE_URL+pimgOne;
        imgTwo = MainActivity.BASE_URL+pimgTwo;
        imgThree = MainActivity.BASE_URL+pimgThree;

        GetMySliderImg sliderImg = new GetMySliderImg();
        sliderImg.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_property_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home)
            finish();

        if (id == R.id.menu_edit)
        {
            Intent i = new Intent(MyPropertyDetailsActivity.this,AddPropertyActivity.class);
            i.putExtra("opt","edit");
            i.putExtra("propId",propId);
            i.putExtra("pid",pid);
            i.putExtra("pprize",pprize);
            i.putExtra("ppbhk",ppbhk);
            i.putExtra("ptname",ptname);
            i.putExtra("pparea",pparea);
            i.putExtra("pyearbuilt",pyearbuilt);
            i.putExtra("pstate",pstate);
            i.putExtra("pcity",pcity);
            i.putExtra("paddress",paddress);
            i.putExtra("pbedroom",pbedroom);
            i.putExtra("pbathroom",pbathroom);
            i.putExtra("pdes",pdes);
            i.putExtra("peid",user_id);
            startActivity(i);
        }
        else if (id == R.id.menu_delete)
        {
            dialog = new Dialog(MyPropertyDetailsActivity.this,android.R.style.Theme_Light_NoTitleBar);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.delete_dialog);
            dialog.setCancelable(true);

            TextView txtNo = (TextView)dialog.findViewById(R.id.txtNo);
            TextView txtYes = (TextView)dialog.findViewById(R.id.txtYes);
            txtYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GetDeleteProperty deleteProperty = new GetDeleteProperty();
                    deleteProperty.execute();
                }
            });

            txtNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        }

        return super.onOptionsItemSelected(item);
    }

    private class GetMySliderImg extends AsyncTask<String,Void,String> implements BaseSliderView.OnSliderClickListener {
        @Override
        protected String doInBackground(String... strings) {

            MySliderImgArray.add(imgOne);
            MySliderImgArray.add(imgTwo);
            MySliderImgArray.add(imgThree);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            for (String name : MySliderImgArray) {
                DefaultSliderView textSliderView = new DefaultSliderView(MyPropertyDetailsActivity.this);
                // initialize a SliderLayout
                textSliderView
                        .image(name)
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                slMySlider.addSlider(textSliderView);
            }
            slMySlider.setCustomAnimation(new DescriptionAnimation());
            slMySlider.setDuration(5000);

        }

        @Override
        public void onSliderClick(BaseSliderView slider) {
            slMySlider.startAutoCycle();
        }
    }

    private class GetDeleteProperty extends AsyncTask<String,Void,String> {

        String status,message;
        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("id",propId);
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"DeleteProperty.php",joUser.toString());
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
                Intent i = new Intent(MyPropertyDetailsActivity.this,MyPropertyActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(MyPropertyDetailsActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
