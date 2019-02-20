package com.iteration.grwa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {

    RecyclerView rvSearchList;
    String typeId;
    EditText txtSearch;
    ImageView ivClose;
    SearchListAdapter searchListAdapter;
    ArrayList<ListPropertyModel> SearchListArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        rvSearchList = (RecyclerView)findViewById(R.id.rvSearchList);
        rvSearchList.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
        rvSearchList.setLayoutManager(manager);

        typeId = getIntent().getExtras().getString("typeId");

        txtSearch = (EditText)findViewById(R.id.txtSearch);
        ivClose = (ImageView)findViewById(R.id.ivClose);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSearch.setText("");
            }
        });

        GetSearchList searchList = new GetSearchList();
        searchList.execute();

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchListAdapter.getFilter().filter(s.toString());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private class GetSearchList extends AsyncTask<String,Void,String> {
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
                        String username =jo.getString("username");
                        String userpic =jo.getString("userpic");
                        String useremail =jo.getString("useremail");
                        String usermobile =jo.getString("usermobile");

                        String filter = pcity+""+pprize;

                        ListPropertyModel listPropertyModel = new ListPropertyModel(id,pid,pimgone,pimgtwo,pimgthree,pprize,ppbhk,ptname,pparea,pyearbuilt,pstate,pcity,paddress,pbedroom,pbathroom,pdes,username,userpic,useremail,usermobile,filter);
                        SearchListArray.add(listPropertyModel);

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
                searchListAdapter = new SearchListAdapter(SearchActivity.this,SearchListArray);
                rvSearchList.setAdapter(searchListAdapter);
            }
            else
            {
                Toast.makeText(SearchActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
