package com.iteration.grwa;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

class ListpropertyAdapter extends RecyclerView.Adapter<ListpropertyAdapter.ViewHolder>{

    Context context;
    ArrayList<HashMap<String, String>> propertiesListArray;
    View v;
    Dialog dialog;
    EditText txtInName,txtInPhone,txtInEmail,txtInMessage;

    public ListpropertyAdapter(Context context, ArrayList<HashMap<String, String>> propertiesListArray) {
        this.context = context;
        this.propertiesListArray = propertiesListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.property_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        String rs = context.getResources().getString(R.string.RS);

        final String id = propertiesListArray.get(position).get("id");
        final String pid = propertiesListArray.get(position).get("pid");
        final String pimgone = propertiesListArray.get(position).get("pimgone");
        final String pimgtwo = propertiesListArray.get(position).get("pimgtwo");
        final String pimgthree = propertiesListArray.get(position).get("pimgthree");
        final String pprize = propertiesListArray.get(position).get("pprize");
        final String ppbhk = propertiesListArray.get(position).get("ppbhk");
        final String ptname = propertiesListArray.get(position).get("ptname");
        final String pparea = propertiesListArray.get(position).get("pparea");
        final String pyearbuilt = propertiesListArray.get(position).get("pyearbuilt");
        final String pstate = propertiesListArray.get(position).get("pstate");
        final String pcity = propertiesListArray.get(position).get("pcity");
        final String paddress = propertiesListArray.get(position).get("paddress");
        final String pbedroom = propertiesListArray.get(position).get("pbedroom");
        final String pbathroom = propertiesListArray.get(position).get("pbathroom");
        final String pdes = propertiesListArray.get(position).get("pdes");
        final String eid = propertiesListArray.get(position).get("eid");
        final String username = propertiesListArray.get(position).get("username");
        final String userpic = propertiesListArray.get(position).get("userpic");
        final String useremail = propertiesListArray.get(position).get("useremail");
        final String usermobile = propertiesListArray.get(position).get("usermobile");

        holder.txtPropertyName.setText(ppbhk+" BHK "+ptname+" "+pcity);
        holder.txtPropertyPrice.setText(rs +" "+pprize);
        holder.txtPropertyLocation.setText(pparea+" Sq.Ft");

        String imgOne = MainActivity.BASE_URL+pimgone;
        Picasso.with(context).load(imgOne).into(holder.ivPropertyImg);

        holder.btnPropInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context,android.R.style.Theme_Light_NoTitleBar);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.inquiry_dialog);
                dialog.setCancelable(true);

                txtInName = (EditText)dialog.findViewById(R.id.txtInName);
                txtInPhone = (EditText)dialog.findViewById(R.id.txtInPhone);
                txtInEmail = (EditText)dialog.findViewById(R.id.txtInEmail);
                txtInMessage = (EditText)dialog.findViewById(R.id.txtInMessage);

                Button btnInInquire = (Button)dialog.findViewById(R.id.btnInInquire);
                btnInInquire.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(txtInName.getText().toString().equals("") && txtInPhone.getText().toString().equals("") && txtInEmail.getText().toString().equals("") && txtInMessage.getText().toString().equals(""))
                        {
                            Toast.makeText(context,"Enter Your Name.",Toast.LENGTH_SHORT).show();
                        }
                        else if (txtInPhone.getText().toString().equals("") && txtInEmail.getText().toString().equals("") && txtInMessage.getText().toString().equals(""))
                        {
                            Toast.makeText(context,"Enter Your Mobile.",Toast.LENGTH_SHORT).show();
                        }
                        else if (txtInEmail.getText().toString().equals("") && txtInMessage.getText().toString().equals(""))
                        {
                            Toast.makeText(context,"Enter Your Email.",Toast.LENGTH_SHORT).show();
                        }
                        else if (txtInMessage.getText().toString().equals(""))
                        {
                            Toast.makeText(context,"Enter Your Message.",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            GetInsInquire insInquire = new GetInsInquire(eid);
                            insInquire.execute();
                        }

                    }
                });

                LinearLayout llInquiryDialog = (LinearLayout)dialog.findViewById(R.id.llInquiryDialog);
                llInquiryDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,PropertyDetailsActivity.class);
                i.putExtra("id",id);
                i.putExtra("pid",pid);
                i.putExtra("pimgone",pimgone);
                i.putExtra("pimgtwo",pimgtwo);
                i.putExtra("pimgthree",pimgthree);
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
                i.putExtra("eid",eid);
                i.putExtra("username",username);
                i.putExtra("userpic",userpic);
                i.putExtra("useremail",useremail);
                i.putExtra("usermobile",usermobile);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return propertiesListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPropertyImg;
        TextView txtPropertyName,txtPropertyLocation,txtPropertyPrice;
        LinearLayout btnPropInquiry;

        public ViewHolder(View itemView) {
            super(itemView);

            ivPropertyImg = (ImageView)itemView.findViewById(R.id.ivPropertyImg);
            txtPropertyName = (TextView)itemView.findViewById(R.id.txtPropertyName);
            txtPropertyLocation = (TextView)itemView.findViewById(R.id.txtPropertyLocation);
            txtPropertyPrice = (TextView)itemView.findViewById(R.id.txtPropertyPrice);
            btnPropInquiry = (LinearLayout) itemView.findViewById(R.id.btnPropInquire);

        }
    }

    private class GetInsInquire extends AsyncTask<String,Void,String> {

        String status,message,eid;

        public GetInsInquire(String eid) {
            this.eid = eid;
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject joUser=new JSONObject();
            try {

                joUser.put("i_name",txtInName.getText().toString());
                joUser.put("i_phone",txtInPhone.getText().toString());
                joUser.put("i_email",txtInEmail.getText().toString());
                joUser.put("i_message",txtInMessage.getText().toString());
                joUser.put("i_e_id",eid);

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"insertInquiry.php",joUser.toString());
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
                txtInName.setText("");
                txtInPhone.setText("");
                txtInEmail.setText("");
                txtInMessage.setText("");
                Toast.makeText(context,"Submit",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
            else
            {
                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
