package com.iteration.grwa;

import android.content.Context;
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
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.ArrayList;

public class ViewAllPropertyActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rvPropertyList;
    ArrayList<String> propertyImgArray = new ArrayList<>();
    ArrayList<String> propertyNameArray = new ArrayList<>();
    ArrayList<String> propertyLocationArray = new ArrayList<>();
    ArrayList<String> propertyPriceArray = new ArrayList<>();
    ArrayList<String> propertyTypeArray = new ArrayList<>();

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

            /*================== Property List view ==================*/

        rvPropertyList = (RecyclerView)findViewById(R.id.rvPropertyList);
        rvPropertyList.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rvPropertyList.setLayoutManager(manager);

        GetPropertyList propertyList = new GetPropertyList();
        propertyList.execute();


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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_pro) {
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

    private class GetPropertyList extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {

            propertyImgArray.add("http://cdn.woodynody.com/2016/03/12/75-x-125-2-kanal-house-plan-layout-3d-front-elevation.jpg");
            propertyImgArray.add("http://cdn.woodynody.com/2016/03/12/75-x-125-2-kanal-house-plan-layout-3d-front-elevation.jpg");
            propertyImgArray.add("http://cdn.woodynody.com/2016/03/12/75-x-125-2-kanal-house-plan-layout-3d-front-elevation.jpg");
            propertyImgArray.add("http://cdn.woodynody.com/2016/03/12/75-x-125-2-kanal-house-plan-layout-3d-front-elevation.jpg");
            propertyImgArray.add("http://cdn.woodynody.com/2016/03/12/75-x-125-2-kanal-house-plan-layout-3d-front-elevation.jpg");
            propertyImgArray.add("http://cdn.woodynody.com/2016/03/12/75-x-125-2-kanal-house-plan-layout-3d-front-elevation.jpg");
            propertyImgArray.add("http://cdn.woodynody.com/2016/03/12/75-x-125-2-kanal-house-plan-layout-3d-front-elevation.jpg");
            propertyImgArray.add("http://cdn.woodynody.com/2016/03/12/75-x-125-2-kanal-house-plan-layout-3d-front-elevation.jpg");

            propertyNameArray.add("Kanal House Plan");
            propertyNameArray.add("Kanal House Plan");
            propertyNameArray.add("Kanal House Plan");
            propertyNameArray.add("Kanal House Plan");
            propertyNameArray.add("Kanal House Plan");
            propertyNameArray.add("Kanal House Plan");
            propertyNameArray.add("Kanal House Plan");
            propertyNameArray.add("Kanal House Plan");

            propertyLocationArray.add("Kudasan Gandhinager");
            propertyLocationArray.add("Kudasan Gandhinager");
            propertyLocationArray.add("Kudasan Gandhinager");
            propertyLocationArray.add("Kudasan Gandhinager");
            propertyLocationArray.add("Kudasan Gandhinager");
            propertyLocationArray.add("Kudasan Gandhinager");
            propertyLocationArray.add("Kudasan Gandhinager");
            propertyLocationArray.add("Kudasan Gandhinager");

            propertyPriceArray.add("₹ 45.8L - 1.28 Cr");
            propertyPriceArray.add("₹ 45.8L - 1.28 Cr");
            propertyPriceArray.add("₹ 45.8L - 1.28 Cr");
            propertyPriceArray.add("₹ 45.8L - 1.28 Cr");
            propertyPriceArray.add("₹ 45.8L - 1.28 Cr");
            propertyPriceArray.add("₹ 45.8L - 1.28 Cr");
            propertyPriceArray.add("₹ 45.8L - 1.28 Cr");
            propertyPriceArray.add("₹ 45.8L - 1.28 Cr");

            propertyTypeArray.add("5 BHK House");
            propertyTypeArray.add("5 BHK House");
            propertyTypeArray.add("5 BHK House");
            propertyTypeArray.add("5 BHK House");
            propertyTypeArray.add("5 BHK House");
            propertyTypeArray.add("5 BHK House");
            propertyTypeArray.add("5 BHK House");
            propertyTypeArray.add("5 BHK House");

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ListpropertyAdapter listpropertyAdapter = new ListpropertyAdapter(ViewAllPropertyActivity.this,propertyImgArray,propertyNameArray,propertyLocationArray,propertyPriceArray,propertyTypeArray);
            rvPropertyList.setAdapter(listpropertyAdapter);
        }
    }
}
