package com.example.travelmantics;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//will send data to recylerview through the viewholder
//viewholders get cached to make the view cleaner
public class DealAdapter extends  RecyclerView.Adapter<DealAdapter.DealViewHolder>{

    @NonNull
    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DealViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        public  DealViewHolder(View itemView){
            super(itemView);
            tvTitle=(TextView)itemView.findViewById(R.id.tvTitle);
        }


        public void bind(TravelDeal deal){
            tvTitle.setText(deal.getTitle());
        }

    }
}
