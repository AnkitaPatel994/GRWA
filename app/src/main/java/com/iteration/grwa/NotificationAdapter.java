package com.iteration.grwa;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    Context context;
    View v;
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

        String id = notificationListArray.get(i).get("i_id");
        String name = notificationListArray.get(i).get("i_name");
        String phone = notificationListArray.get(i).get("i_phone");
        String email = notificationListArray.get(i).get("i_email");
        String message = notificationListArray.get(i).get("i_message");

        holder.txtNName.setText(name);
        holder.txtNPhone.setText("+91 "+phone);
        holder.txtNEmail.setText(email);
        holder.txtNMessage.setText(message);

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
}
