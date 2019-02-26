package com.iteration.grwa;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    Context context;
    View v;
    Dialog dialog;
    ArrayList<HashMap<String, String>> notificationListArray;

    public NotificationAdapter(Context context, ArrayList<HashMap<String, String>> notificationListArray) {
        this.context = context;
        this.notificationListArray = notificationListArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notification_list, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        final String id = notificationListArray.get(i).get("i_id");
        String name = notificationListArray.get(i).get("i_name");
        String phone = notificationListArray.get(i).get("i_phone");
        String email = notificationListArray.get(i).get("i_email");
        String message = notificationListArray.get(i).get("i_message");

        holder.txtNName.setText(name);
        holder.txtNPhone.setText("+91 "+phone);
        holder.txtNEmail.setText(email);
        holder.txtNMessage.setText(message);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context,android.R.style.Theme_Light_NoTitleBar);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.delete_dialog);
                dialog.setCancelable(true);

                TextView txtDeleteTitle = (TextView)dialog.findViewById(R.id.txtDeleteTitle);
                txtDeleteTitle.setText("Delete Notification");
                TextView txtNo = (TextView)dialog.findViewById(R.id.txtNo);
                TextView txtYes = (TextView)dialog.findViewById(R.id.txtYes);
                txtYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetDeleteNotification deleteProperty = new GetDeleteNotification(id);
                        deleteProperty.execute();
                    }
                });

                txtNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return notificationListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNName,txtNPhone,txtNEmail,txtNMessage;
        LinearLayout txtNReadMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNName = (TextView)itemView.findViewById(R.id.txtNName);
            txtNPhone = (TextView)itemView.findViewById(R.id.txtNPhone);
            txtNEmail = (TextView)itemView.findViewById(R.id.txtNEmail);
            txtNMessage = (TextView)itemView.findViewById(R.id.txtNMessage);
            txtNReadMore = (LinearLayout)itemView.findViewById(R.id.txtNReadMore);

        }
    }

    private class GetDeleteNotification extends AsyncTask<String,Void,String> {

        String status,message,id;

        public GetDeleteNotification(String id) {
            this.id = id;
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("id",id);
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"DeleteNotification.php",joUser.toString());
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
                dialog.dismiss();
                Intent i = new Intent(context,NotificationActivity.class);
                context.startActivity(i);
            }
            else
            {
                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
