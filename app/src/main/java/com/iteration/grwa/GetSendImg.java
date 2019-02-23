package com.iteration.grwa;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class GetSendImg extends AsyncTask<String,Void,String> {

    Context context;
    String encodedImgpath,userId,status,message;

    public GetSendImg(Context context, String userId, String encodedImgpath) {
        this.context = context;
        this.userId = userId;
        this.encodedImgpath = encodedImgpath;
    }

    @Override
    protected String doInBackground(String... strings) {

        JSONObject joUser=new JSONObject();
        try {
            joUser.put("e_id",userId);
            joUser.put("e_pic",encodedImgpath);
            Postdata postdata = new Postdata();
            String pdUser=postdata.post(MainActivity.BASE_URL+"editEmployImg.php",joUser.toString());
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
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        }
    }
}
