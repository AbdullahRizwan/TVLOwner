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
import com.example.tvlonwer.model.VehicleUser;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class Adapter_Select_Current_Vehicle extends RecyclerView.Adapter<Adapter_Select_Current_Vehicle.ViewHolder> implements Filterable {
    private ArrayList<VehicleUser> data;
    protected ArrayList<VehicleUser> temp;
    private Context c;
    private Adapter_Select_Current_Vehicle.OnClickListener myListener;

    @Override
    public Adapter_Select_Current_Vehicle.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_name_licenseno, parent, false);
        return new Adapter_Select_Current_Vehicle.ViewHolder(view,myListener);
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_Select_Current_Vehicle.ViewHolder holder, int position) {
        if(temp.size() > position) {
            holder.plateNo.setText(temp.get(position).getPlateNo());
            /*holder.kilometers.setText(temp.get(position).getKilometers());*/
        }
        else {
            Toast.makeText(c,"Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }

    public void setData(MutableLiveData<ArrayList<VehicleUser> >d, Context context, Adapter_Select_Current_Vehicle.OnClickListener click) {
        data=d.getValue();
        temp = new ArrayList<>();
        temp.addAll(Objects.requireNonNull(d.getValue()));
        c = context;
        myListener = (OnClickListener) click;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView kilometers;
        TextView plateNo;

        Adapter_Select_Current_Vehicle.OnClickListener clistener;

        public ViewHolder(@NonNull View itemView, Adapter_Select_Current_Vehicle.OnClickListener myListener) {
            super(itemView);
            plateNo = itemView.findViewById(R.id.plate_no);
            kilometers=itemView.findViewById(R.id.kms);

            clistener = myListener;
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            Log.d("TAG", "onClick: " + getAdapterPosition());
            myListener.onUserVehicleClick(temp.get(getAdapterPosition()));
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
                    if (item.getPlateNo().toLowerCase().contains(filterPattern)) {
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
        void onUserVehicleClick(VehicleUser vehicle);
    }
}
