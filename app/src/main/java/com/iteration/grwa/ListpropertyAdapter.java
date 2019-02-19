package com.iteration.grwa;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

class ListpropertyAdapter extends RecyclerView.Adapter<ListpropertyAdapter.ViewHolder>{

    Context context;
    ArrayList<HashMap<String, String>> propertiesListArray;
    View v;

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
    public void onBindViewHolder(ViewHolder holder, int position) {

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

        holder.txtPropertyName.setText(ppbhk+" "+ptname+" "+pcity);
        holder.txtPropertyLocation.setText("Beds: "+pbedroom+"  Baths: "+pbathroom);
        holder.txtPropertyPrice.setText(rs +" "+pprize);
        holder.txtPropertyType.setText(pparea);

        String imgOne = MainActivity.BASE_URL+pimgone;
        Picasso.with(context).load(imgOne).into(holder.ivPropertyImg);

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
        TextView txtPropertyName,txtPropertyLocation,txtPropertyPrice,txtPropertyType;

        public ViewHolder(View itemView) {
            super(itemView);

            ivPropertyImg = (ImageView)itemView.findViewById(R.id.ivPropertyImg);
            txtPropertyName = (TextView)itemView.findViewById(R.id.txtPropertyName);
            txtPropertyLocation = (TextView)itemView.findViewById(R.id.txtPropertyLocation);
            txtPropertyPrice = (TextView)itemView.findViewById(R.id.txtPropertyPrice);
            txtPropertyType = (TextView)itemView.findViewById(R.id.txtPropertyType);

        }
    }
}
