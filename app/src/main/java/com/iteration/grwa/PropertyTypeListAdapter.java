package com.iteration.grwa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

class PropertyTypeListAdapter extends RecyclerView.Adapter<PropertyTypeListAdapter.ViewHolder>{

    Context context;
    ArrayList<HashMap<String, String>> propertyTypeListArray;
    View v;

    public PropertyTypeListAdapter(Context context, ArrayList<HashMap<String, String>> propertyTypeListArray) {
        this.context = context;
        this.propertyTypeListArray = propertyTypeListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.property_type_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {

        final String id = propertyTypeListArray.get(i).get("id");
        String propType = propertyTypeListArray.get(i).get("PropType");
        String propImg = propertyTypeListArray.get(i).get("PropImg");

        holder.txtPropertyTypeName.setText(propType);
        Picasso.with(context).load(propImg).into(holder.ivPropertyTypeImg);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,ViewAllPropertyActivity.class);
                i.putExtra("id",id);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return propertyTypeListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPropertyTypeImg;
        TextView txtPropertyTypeName;

        public ViewHolder(View itemView) {
            super(itemView);

            ivPropertyTypeImg = (ImageView)itemView.findViewById(R.id.ivPropertyTypeImg);
            txtPropertyTypeName = (TextView)itemView.findViewById(R.id.txtPropertyTypeName);
        }
    }

}
