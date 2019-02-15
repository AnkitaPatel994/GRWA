package com.iteration.grwa;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class ListpropertyAdapter extends RecyclerView.Adapter<ListpropertyAdapter.ViewHolder>{

    Context context;
    ArrayList<String> propertyImgArray;
    ArrayList<String> propertyNameArray;
    ArrayList<String> propertyLocationArray;
    ArrayList<String> propertyPriceArray;
    ArrayList<String> propertyTypeArray;
    View v;
    int lastPosition = -1;

    public ListpropertyAdapter(Context context, ArrayList<String> propertyImgArray, ArrayList<String> propertyNameArray, ArrayList<String> propertyLocationArray, ArrayList<String> propertyPriceArray, ArrayList<String> propertyTypeArray) {

        this.context = context;
        this.propertyImgArray = propertyImgArray;
        this.propertyNameArray = propertyNameArray;
        this.propertyLocationArray = propertyLocationArray;
        this.propertyPriceArray = propertyPriceArray;
        this.propertyTypeArray = propertyTypeArray;

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

        String Img = propertyImgArray.get(position);
        String Name = propertyNameArray.get(position);
        String Location = propertyLocationArray.get(position);
        String Price = propertyPriceArray.get(position);
        String Type = propertyTypeArray.get(position);

        holder.txtPropertyName.setText(Name);
        holder.txtPropertyLocation.setText(Location);
        holder.txtPropertyPrice.setText(Price);
        holder.txtPropertyType.setText(Type);

    }

    @Override
    public int getItemCount() {
        return propertyImgArray.size();
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
