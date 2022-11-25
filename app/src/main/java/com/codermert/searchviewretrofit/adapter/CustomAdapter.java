package com.codermert.searchviewretrofit.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codermert.searchviewretrofit.CustomItemClickListener;
import com.codermert.searchviewretrofit.R;
import com.codermert.searchviewretrofit.model.User;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {

    private ArrayList<User> userList;
    private ArrayList<User> filteredUserList;
    private Context context;
    private CustomItemClickListener customItemClickListener;

    public CustomAdapter(Context context, ArrayList<User> userArrayList, CustomItemClickListener customItemClickListener) {
        this.context = context;
        this.userList = userArrayList;
        this.filteredUserList = userArrayList;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for click item listener
                customItemClickListener.onItemClick(filteredUserList.get(myViewHolder.getAdapterPosition()),myViewHolder.getAdapterPosition());
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdapter.MyViewHolder viewHolder, int position) {

        viewHolder.userName.setText(filteredUserList.get(position).getUsername());
        viewHolder.userCity.setText(filteredUserList.get(position).getAddress().getCity());
        viewHolder.userWeb.setText(filteredUserList.get(position).getWebsite());
        viewHolder.userEmail.setText(filteredUserList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {

                    filteredUserList = userList;

                } else {

                    ArrayList<User> tempFilteredList = new ArrayList<>();

                    for (User user : userList) {

                        // search for user name
                        if (user.getUsername().toLowerCase().contains(searchString)) {

                            tempFilteredList.add(user);
                        }
                    }

                    filteredUserList = tempFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUserList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUserList = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private TextView userEmail;
        private TextView userCity;
        private TextView userWeb;
        public MyViewHolder(View view) {
            super(view);
            userName = (TextView)view.findViewById(R.id.userName);
            userEmail = (TextView)view.findViewById(R.id.userEmail);
            userWeb = (TextView)view.findViewById(R.id.userWebPage);
            userCity = (TextView)view.findViewById(R.id.userCity);

        }
    }
}