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

class PropertyTypeListAdapter extends RecyclerView.Adapter<PropertyTypeListAdapter.ViewHolder>{

    Context context;
    ArrayList<String> propertyTypeNameListArray;
    ArrayList<String> propertyTypeImgListArray;
    View v;

    public PropertyTypeListAdapter(Context context, ArrayList<String> propertyTypeNameListArray, ArrayList<String> propertyTypeImgListArray) {

        this.context = context;
        this.propertyTypeNameListArray = propertyTypeNameListArray;
        this.propertyTypeImgListArray = propertyTypeImgListArray;

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

        String name = propertyTypeNameListArray.get(i);
        String img = propertyTypeImgListArray.get(i);

        holder.txtPropertyTypeName.setText(name);
        Picasso.with(context).load(img).into(holder.ivPropertyTypeImg);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,ViewAllPropertyActivity.class);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return propertyTypeNameListArray.size();
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
