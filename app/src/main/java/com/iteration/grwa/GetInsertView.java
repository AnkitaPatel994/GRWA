package com.iteration.grwa;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

class GetInsertView extends AsyncTask<String,Void,String> {

    String pid,peid,status,message;
    Context context;

    public GetInsertView(Context context, String pid, String peid) {
        this.context = context;
        this.pid = pid;
        this.peid = peid;
    }

    @Override
    protected String doInBackground(String... strings) {
        JSONObject joUser=new JSONObject();
        try {
            joUser.put("v_ppid",pid);
            joUser.put("v_eid",peid);
            Postdata postdata = new Postdata();
            String pdUser=postdata.post(MainActivity.BASE_URL+"insertView.php",joUser.toString());
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
            //Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
        }
    }
}
