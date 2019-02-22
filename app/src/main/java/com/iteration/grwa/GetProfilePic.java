package com.iteration.grwa;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

class GetProfilePic extends AsyncTask<String,Void,String> {

    Context context;
    String user_id,status,message,ProfilepImg;
    CircleImageView ivUserImg;

    public GetProfilePic(Context context, String user_id, CircleImageView ivUserImg) {
        this.context = context;
        this.user_id = user_id;
        this.ivUserImg = ivUserImg;
    }

    @Override
    protected String doInBackground(String... strings) {

        JSONObject joUser=new JSONObject();
        try {
            joUser.put("UserId",user_id);
            Postdata postdata = new Postdata();
            String pdUser=postdata.post(MainActivity.BASE_URL+"ViewEmployee.php",joUser.toString());
            JSONObject j = new JSONObject(pdUser);
            status=j.getString("status");
            if(status.equals("1"))
            {
                message=j.getString("message");
                JSONArray JsArry=j.getJSONArray("Employee");
                for (int i=0;i<JsArry.length();i++)
                {
                    JSONObject jo=JsArry.getJSONObject(i);

                    String e_pic =jo.getString("e_pic");
                    ProfilepImg =MainActivity.BASE_URL+e_pic;

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
            Picasso.with(context).load(ProfilepImg).into(ivUserImg);
        }
        else
        {
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        }
    }
}
