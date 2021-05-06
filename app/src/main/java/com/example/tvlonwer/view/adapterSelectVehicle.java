package com.example.tvlonwer.view;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class adapterSelectVehicle extends RecyclerView.Adapter<adapterSelectVehicle.ViewHolder> implements Filterable {
    private ArrayList<Vehicle> data;
    protected ArrayList<Vehicle> temp;
    private Context c;
    private OnClickListener myListener;

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_row_vehicle, parent, false);
        return new ViewHolder(view,myListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(temp.size() > position) {
            holder.make.setText(temp.get(position).getMake());
            holder.model.setText(temp.get(position).getModel());
            holder.year.setText(temp.get(position).getYear());
        }
        else {
            Toast.makeText(c,"Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }

    public void setData(MutableLiveData<ArrayList<Vehicle>> d, Context context, OnClickListener click) {
        data = d.getValue();
        temp = new ArrayList<>();
        temp.addAll(Objects.requireNonNull(d.getValue()));
        c = context;
        myListener = click;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView model;
        TextView make;
        TextView year;
        OnClickListener clistener;

        public ViewHolder(@NonNull View itemView, OnClickListener myListener) {
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
            myListener.onVehicleClick(temp.get(getAdapterPosition()));
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFiler;
    }
    Filter exampleFiler = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Vehicle> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(data);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Vehicle item : data) {
                    if (item.getMake().toLowerCase().contains(filterPattern)) {
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