package com.example.tvlonwer.view.ui.gallery;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvlonwer.R;
import com.example.tvlonwer.model.Vehicle;
import com.example.tvlonwer.model.VehicleUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class gallaryRecyclerViewAdapter extends RecyclerView.Adapter<gallaryRecyclerViewAdapter.ViewHolder> implements Filterable {
    private ArrayList<VehicleUser> data;
    protected ArrayList<VehicleUser> temp;
    private Context c;
    private gallaryRecyclerViewAdapter.OnClickListener myListener;

    @Override
    public gallaryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_row_vehicle, parent, false);
        return new gallaryRecyclerViewAdapter.ViewHolder(view,myListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(temp.size() > position) {
            holder.make.setText(temp.get(position).getVehicle().getMake());
            holder.model.setText(temp.get(position).getVehicle().getModel());
            holder.year.setText(temp.get(position).getPlateNo());
        }
        else {
            Toast.makeText(c,"Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }

    public void setData(MutableLiveData<ArrayList<VehicleUser>> d, Context context, gallaryRecyclerViewAdapter.OnClickListener click) {
        data = d.getValue();
        temp = new ArrayList<>();
        temp.addAll(Objects.requireNonNull(d.getValue()));
        c = context;
        myListener =  click;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView model;
        TextView make;
        TextView year;
        gallaryRecyclerViewAdapter.OnClickListener clistener;

        public ViewHolder(@NonNull View itemView, gallaryRecyclerViewAdapter.OnClickListener myListener) {
            super(itemView);
            model = itemView.findViewById(R.id.model);
            make = itemView.findViewById(R.id.make);
            year = itemView.findViewById(R.id.year);
            clistener = myListener;
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            Log.d("TAG", "onClick: " + getAdapterPosition());
            myListener.onVehicleClick(temp.get(getAdapterPosition()).getVehicle());
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFiler;
    }
    Filter exampleFiler = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<VehicleUser> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(data);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (VehicleUser item : data) {
                    if (item.getVehicle().getMake().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            temp.clear();
            temp.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    public interface OnClickListener{
        void onVehicleClick(Vehicle vehicle);
    }

}