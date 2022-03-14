package com.example.fininfocomtask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {
    ArrayList<recyclerModel> itemList;

    public CustomAdapter(ArrayList<recyclerModel> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public CustomAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_weight,parent,false);

        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.Holder holder, int position) {

        holder.Single_Email.setText(itemList.get(position).getEmail());
        holder.Single_Phone.setText(itemList.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView Single_Email,Single_Phone;
        public Holder(@NonNull View itemView) {
            super(itemView);

            Single_Email=itemView.findViewById(R.id.singleEmail);
            Single_Phone=itemView.findViewById(R.id.singlePhone);
        }
    }
}
