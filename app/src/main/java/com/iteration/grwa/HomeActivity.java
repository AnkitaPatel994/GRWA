package com.iteration.grwa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SliderLayout slSlider;
    ArrayList<String> SliderImgArray = new ArrayList<>();
    RecyclerView rvPropertyTypeList;
    ArrayList<String> PropertyTypeNameListArray = new ArrayList<>();
    ArrayList<String> PropertyTypeImgListArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*slSlider = (SliderLayout)findViewById(R.id.slSlider);
        GetSliderImg sliderImg = new GetSliderImg();
        sliderImg.execute();*/

        /*================== Property List view ==================*/

        rvPropertyTypeList = (RecyclerView)findViewById(R.id.rvPropertyTypeList);
        rvPropertyTypeList.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rvPropertyTypeList.setLayoutManager(manager);

        GetPropertyTypeList propertyTypeList = new GetPropertyTypeList();
        propertyTypeList.execute();



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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class GetSliderImg extends AsyncTask<String,Void,String> implements BaseSliderView.OnSliderClickListener {
        @Override
        protected String doInBackground(String... strings) {

            SliderImgArray.add("http://cdn.woodynody.com/2016/03/12/75-x-125-2-kanal-house-plan-layout-3d-front-elevation.jpg");
            SliderImgArray.add("https://w.ndtvimg.com/sites/3/2014/02/29125202/Pace-Prana.jpg");
            SliderImgArray.add("https://assets.entrepreneur.com/content/3x2/2000/20161005213936-rental-homes-balconies.jpeg?width=700&crop=2:1");

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            for (String name : SliderImgArray) {
                DefaultSliderView textSliderView = new DefaultSliderView(HomeActivity.this);
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

    private class GetPropertyTypeList extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {

            PropertyTypeNameListArray.add("Flat");
            PropertyTypeNameListArray.add("House");
            PropertyTypeNameListArray.add("Plot");

            PropertyTypeImgListArray.add("https://w.ndtvimg.com/sites/3/2014/02/29125202/Pace-Prana.jpg");
            PropertyTypeImgListArray.add("http://cdn.woodynody.com/2016/03/12/75-x-125-2-kanal-house-plan-layout-3d-front-elevation.jpg");
            PropertyTypeImgListArray.add("https://assets.entrepreneur.com/content/3x2/2000/20161005213936-rental-homes-balconies.jpeg?width=700&crop=2:1");

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            PropertyTypeListAdapter propertyTypeListAdapter = new PropertyTypeListAdapter(HomeActivity.this,PropertyTypeNameListArray,PropertyTypeImgListArray);
            rvPropertyTypeList.setAdapter(propertyTypeListAdapter);
        }
    }
}
