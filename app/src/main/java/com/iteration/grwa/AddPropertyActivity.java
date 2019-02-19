package com.iteration.grwa;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Spinner;

public class AddPropertyActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText txtAPId,txtAPPrize,txtAPBHK,txtAPArea,txtAPYearBuilt,txtAPPropDes,txtAPBedroom,txtAPCity,txtAPState,txtAPBathroom,txtAPAddress;
    LinearLayout llImgOne,llImgTwo,llImgThree;
    ImageView ivImgOne,ivImgTwo,ivImgThree;
    Spinner spPType;
    Button btnAddProp;

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

        txtAPArea = (EditText) findViewById(R.id.txtAPArea);
        txtAPYearBuilt = (EditText) findViewById(R.id.txtAPYearBuilt);
        txtAPBedroom = (EditText) findViewById(R.id.txtAPBedroom);
        txtAPBathroom = (EditText) findViewById(R.id.txtAPBathroom);
        txtAPAddress = (EditText) findViewById(R.id.txtAPAddress);
        txtAPCity = (EditText) findViewById(R.id.txtAPCity);
        txtAPState = (EditText) findViewById(R.id.txtAPState);
        txtAPPropDes = (EditText) findViewById(R.id.txtAPPropDes);

        btnAddProp = (Button)findViewById(R.id.btnAddProp);
        btnAddProp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

        /*if (id == R.id.nav_add_pro) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
