package com.iteration.grwa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<ListPropertyModel> SearchListArray;
    ArrayList<ListPropertyModel> SearchListFilter;
    View v;

    public SearchListAdapter(Context context, ArrayList<ListPropertyModel> SearchListArray) {
        this.context = context;
        this.SearchListArray = SearchListArray;
        this.SearchListFilter = new ArrayList<>(SearchListArray);
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

        final String id = SearchListArray.get(position).getId();
        final String pid = SearchListArray.get(position).getPid();
        final String pimgone = SearchListArray.get(position).getPimgone();
        final String pimgtwo = SearchListArray.get(position).getPimgtwo();
        final String pimgthree = SearchListArray.get(position).getPimgthree();
        final String pprize = SearchListArray.get(position).getPprize();
        final String ppbhk = SearchListArray.get(position).getPpbhk();
        final String ptname = SearchListArray.get(position).getPtname();
        final String pparea = SearchListArray.get(position).getPparea();
        final String pyearbuilt = SearchListArray.get(position).getPyearbuilt();
        final String pstate = SearchListArray.get(position).getPstate();
        final String pcity = SearchListArray.get(position).getPcity();
        final String paddress = SearchListArray.get(position).getPaddress();
        final String pbedroom = SearchListArray.get(position).getPbedroom();
        final String pbathroom = SearchListArray.get(position).getPbathroom();
        final String pdes = SearchListArray.get(position).getPdes();
        final String username = SearchListArray.get(position).getUsername();
        final String userpic = SearchListArray.get(position).getUserpic();
        final String useremail = SearchListArray.get(position).getUseremail();
        final String usermobile = SearchListArray.get(position).getUsermobile();

        String filter = SearchListArray.get(position).getFilter();

        holder.txtPropertyName.setText(ppbhk + " " + ptname + " " + pcity);
        holder.txtPropertyLocation.setText("Beds: " + pbedroom + "  Baths: " + pbathroom);
        holder.txtPropertyPrice.setText(rs + " " + pprize);
        holder.txtPropertyType.setText(pparea);

        String imgOne = MainActivity.BASE_URL + pimgone;
        Picasso.with(context).load(imgOne).into(holder.ivPropertyImg);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PropertyDetailsActivity.class);
                i.putExtra("id", id);
                i.putExtra("pid", pid);
                i.putExtra("pimgone", pimgone);
                i.putExtra("pimgtwo", pimgtwo);
                i.putExtra("pimgthree", pimgthree);
                i.putExtra("pprize", pprize);
                i.putExtra("ppbhk", ppbhk);
                i.putExtra("ptname", ptname);
                i.putExtra("pparea", pparea);
                i.putExtra("pyearbuilt", pyearbuilt);
                i.putExtra("pstate", pstate);
                i.putExtra("pcity", pcity);
                i.putExtra("paddress", paddress);
                i.putExtra("pbedroom", pbedroom);
                i.putExtra("pbathroom", pbathroom);
                i.putExtra("pdes", pdes);
                i.putExtra("username", username);
                i.putExtra("userpic", userpic);
                i.putExtra("useremail", useremail);
                i.putExtra("usermobile", usermobile);
                context.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return SearchListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPropertyImg;
        TextView txtPropertyName, txtPropertyLocation, txtPropertyPrice, txtPropertyType;

        public ViewHolder(View itemView) {
            super(itemView);

            ivPropertyImg = (ImageView) itemView.findViewById(R.id.ivPropertyImg);
            txtPropertyName = (TextView) itemView.findViewById(R.id.txtPropertyName);
            txtPropertyLocation = (TextView) itemView.findViewById(R.id.txtPropertyLocation);
            txtPropertyPrice = (TextView) itemView.findViewById(R.id.txtPropertyPrice);
            txtPropertyType = (TextView) itemView.findViewById(R.id.txtPropertyType);

        }
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<ListPropertyModel> filterdNames = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filterdNames.addAll(SearchListFilter);
            } else {
                String filterPatten = constraint.toString().toLowerCase().trim();
                for (ListPropertyModel name : SearchListFilter) {
                    //if the existing elements contains the search input
                    if (name.getFilter().toLowerCase().contains(filterPatten)) {
                        //adding the element to filtered list
                        filterdNames.add(name);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterdNames;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            SearchListArray.clear();
            SearchListArray.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }

    };
}