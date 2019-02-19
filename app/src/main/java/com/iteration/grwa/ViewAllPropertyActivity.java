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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewAllPropertyActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rvPropertyList;
    String typeId;
    ArrayList<HashMap<String,String>> PropertiesListArray = new ArrayList<>();

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

        typeId = getIntent().getExtras().getString("id");

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

        int id = item.getItemId();

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
