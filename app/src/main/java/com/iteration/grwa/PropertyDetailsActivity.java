package com.iteration.grwa;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;

public class PropertyDetailsActivity extends AppCompatActivity {

    SliderLayout slSlider;
    ArrayList<String> SliderImgArray = new ArrayList<>();

    TextView txtPDPrize;

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
        GetSliderImg sliderImg = new GetSliderImg();
        sliderImg.execute();

        txtPDPrize = (TextView)findViewById(R.id.txtPDPrize);

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

            SliderImgArray.add("http://cdn.woodynody.com/2016/03/12/75-x-125-2-kanal-house-plan-layout-3d-front-elevation.jpg");
            SliderImgArray.add("https://w.ndtvimg.com/sites/3/2014/02/29125202/Pace-Prana.jpg");
            SliderImgArray.add("https://assets.entrepreneur.com/content/3x2/2000/20161005213936-rental-homes-balconies.jpeg?width=700&crop=2:1");
            SliderImgArray.add("https://w.ndtvimg.com/sites/3/2014/02/29125202/Pace-Prana.jpg");

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
