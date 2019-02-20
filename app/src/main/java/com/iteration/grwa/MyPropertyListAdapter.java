package com.iteration.grwa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

class MyPropertyListAdapter extends RecyclerView.Adapter<MyPropertyListAdapter.ViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> myPropertiesListArray;
    View v;

    public MyPropertyListAdapter(Context context, ArrayList<HashMap<String, String>> myPropertiesListArray) {
        this.context = context;
        this.myPropertiesListArray = myPropertiesListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_property_list, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String rs = context.getResources().getString(R.string.RS);

        final String id = myPropertiesListArray.get(position).get("id");
        final String pid = myPropertiesListArray.get(position).get("pid");
        final String pimgone = myPropertiesListArray.get(position).get("pimgone");
        final String pimgtwo = myPropertiesListArray.get(position).get("pimgtwo");
        final String pimgthree = myPropertiesListArray.get(position).get("pimgthree");
        final String pprize = myPropertiesListArray.get(position).get("pprize");
        final String ppbhk = myPropertiesListArray.get(position).get("ppbhk");
        final String ptname = myPropertiesListArray.get(position).get("ptname");
        final String pparea = myPropertiesListArray.get(position).get("pparea");
        final String pyearbuilt = myPropertiesListArray.get(position).get("pyearbuilt");
        final String pstate = myPropertiesListArray.get(position).get("pstate");
        final String pcity = myPropertiesListArray.get(position).get("pcity");
        final String paddress = myPropertiesListArray.get(position).get("paddress");
        final String pbedroom = myPropertiesListArray.get(position).get("pbedroom");
        final String pbathroom = myPropertiesListArray.get(position).get("pbathroom");
        final String pdes = myPropertiesListArray.get(position).get("pdes");
        final String username = myPropertiesListArray.get(position).get("username");
        final String userpic = myPropertiesListArray.get(position).get("userpic");
        final String useremail = myPropertiesListArray.get(position).get("useremail");
        final String usermobile = myPropertiesListArray.get(position).get("usermobile");

        holder.txtMyPropertyName.setText(ppbhk+" BHK "+ptname+" "+pcity);
        holder.txtMyPropertyLocation.setText("Beds: "+pbedroom+"  Baths: "+pbathroom);
        holder.txtMyPropertyPrice.setText(rs +" "+pprize);
        holder.txtMyPropertyType.setText(pparea+" Sq.Ft");

        String imgOne = MainActivity.BASE_URL+pimgone;
        Picasso.with(context).load(imgOne).into(holder.ivMyPropertyImg);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,MyPropertyDetailsActivity.class);
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
        return myPropertiesListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivMyPropertyImg;
        TextView txtMyPropertyName,txtMyPropertyLocation,txtMyPropertyPrice,txtMyPropertyType;

        public ViewHolder(View itemView) {
            super(itemView);

            ivMyPropertyImg = (ImageView)itemView.findViewById(R.id.ivMyPropertyImg);
            txtMyPropertyName = (TextView)itemView.findViewById(R.id.txtMyPropertyName);
            txtMyPropertyLocation = (TextView)itemView.findViewById(R.id.txtMyPropertyLocation);
            txtMyPropertyPrice = (TextView)itemView.findViewById(R.id.txtMyPropertyPrice);
            txtMyPropertyType = (TextView)itemView.findViewById(R.id.txtMyPropertyType);
        }
    }
}
