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
    ArrayList<ListPropertyModel> myPropertiesListArray;
    View v;

    public MyPropertyListAdapter(Context context, ArrayList<ListPropertyModel> myPropertiesListArray) {
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

        final String id = myPropertiesListArray.get(position).getId();
        final String pid = myPropertiesListArray.get(position).getPid();
        final String pimgone = myPropertiesListArray.get(position).getPimgone();
        final String pimgtwo = myPropertiesListArray.get(position).getPimgtwo();
        final String pimgthree = myPropertiesListArray.get(position).getPimgthree();
        final String pprize = myPropertiesListArray.get(position).getPprize();
        final String ppbhk = myPropertiesListArray.get(position).getPpbhk();
        final String ptname = myPropertiesListArray.get(position).getPtname();
        final String pfloor = myPropertiesListArray.get(position).getPfloor();
        final String pblockno = myPropertiesListArray.get(position).getPblockno();
        final String pparea = myPropertiesListArray.get(position).getPparea();
        final String pyearbuilt = myPropertiesListArray.get(position).getPyearbuilt();
        final String pstate = myPropertiesListArray.get(position).getPstate();
        final String pcity = myPropertiesListArray.get(position).getPcity();
        final String paddress = myPropertiesListArray.get(position).getPaddress();
        final String pbedroom = myPropertiesListArray.get(position).getPbedroom();
        final String pbathroom = myPropertiesListArray.get(position).getPbathroom();
        final String pdes = myPropertiesListArray.get(position).getPdes();
        final String peid = myPropertiesListArray.get(position).getPeid();
        final String pdate = myPropertiesListArray.get(position).getPdate();
        final String eid = myPropertiesListArray.get(position).getEid();
        final String username = myPropertiesListArray.get(position).getUsername();
        final String userpic = myPropertiesListArray.get(position).getUserpic();
        final String useremail = myPropertiesListArray.get(position).getUseremail();
        final String usermobile = myPropertiesListArray.get(position).getUsermobile();
        final String COUNT = myPropertiesListArray.get(position).getCOUNT();

        holder.txtMyPropertyName.setText(ppbhk+" BHK "+ptname+" "+pcity);
        holder.txtMyPropertyLocation.setText("Beds: "+pbedroom+"  Baths: "+pbathroom);
        holder.txtMyPropertyPrice.setText(rs +" "+pprize);
        holder.txtMyPropertyType.setText(pparea+" Sq.Ft");

        holder.txtMyPropertyDate.setText("Date: "+pdate);
        holder.txtMyPropertyViewCount.setText("View: " + COUNT);

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
                i.putExtra("pfloor", pfloor);
                i.putExtra("pblockno", pblockno);
                i.putExtra("pparea",pparea);
                i.putExtra("pyearbuilt",pyearbuilt);
                i.putExtra("pstate",pstate);
                i.putExtra("pcity",pcity);
                i.putExtra("paddress",paddress);
                i.putExtra("pbedroom",pbedroom);
                i.putExtra("pbathroom",pbathroom);
                i.putExtra("pdes",pdes);
                i.putExtra("pdate", pdate);
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
        return myPropertiesListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivMyPropertyImg;
        TextView txtMyPropertyName,txtMyPropertyLocation,txtMyPropertyPrice,txtMyPropertyType,txtMyPropertyDate,txtMyPropertyViewCount;

        public ViewHolder(View itemView) {
            super(itemView);

            ivMyPropertyImg = (ImageView)itemView.findViewById(R.id.ivMyPropertyImg);
            txtMyPropertyName = (TextView)itemView.findViewById(R.id.txtMyPropertyName);
            txtMyPropertyLocation = (TextView)itemView.findViewById(R.id.txtMyPropertyLocation);
            txtMyPropertyPrice = (TextView)itemView.findViewById(R.id.txtMyPropertyPrice);
            txtMyPropertyType = (TextView)itemView.findViewById(R.id.txtMyPropertyType);
            txtMyPropertyDate = (TextView)itemView.findViewById(R.id.txtMyPropertyDate);
            txtMyPropertyViewCount = (TextView)itemView.findViewById(R.id.txtMyPropertyViewCount);
        }
    }
}
