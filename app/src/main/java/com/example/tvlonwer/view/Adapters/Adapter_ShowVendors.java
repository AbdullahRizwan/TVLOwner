package com.example.tvlonwer.view.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.tvlonwer.model.Vendor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Adapter_ShowVendors extends RecyclerView.Adapter<Adapter_ShowVendors.ViewHolder> implements Filterable {
    private ArrayList<Vendor> data;
    protected ArrayList<Vendor> temp;
    private Context c;
    private Adapter_ShowVendors.OnClickListener myListener;

    @Override
    public Adapter_ShowVendors.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_vendor_view, parent, false);
        return new Adapter_ShowVendors.ViewHolder(view,myListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_ShowVendors.ViewHolder holder, int position) {
        if(temp.size() > position) {
            holder.name.setText(temp.get(position).getName());
            holder.phone.setText(temp.get(position).getPhone());
            holder.address.setText(temp.get(position).getAddress());
        }
        else {
            Toast.makeText(c,"Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }

    public void setData(MutableLiveData<ArrayList<Vendor>> d, Context context, Adapter_ShowVendors.OnClickListener click) {
        data = d.getValue();
        temp = new ArrayList<>();
        temp.addAll(Objects.requireNonNull(d.getValue()));
        c = context;
        myListener = click;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView phone;
        TextView address;
        Adapter_ShowVendors.OnClickListener clistener;

        public ViewHolder(@NonNull View itemView, Adapter_ShowVendors.OnClickListener myListener) {
            super(itemView);
            name = itemView.findViewById(R.id.vendorName);
            phone = itemView.findViewById(R.id.vendorPhoneNumber);
            address = itemView.findViewById(R.id.vendorAddress);
            clistener = myListener;
            itemView.setOnClickListener(this);
            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nbr = (String) phone.getText();
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData( Uri.parse("tel:"+nbr));
                    c.startActivity(callIntent);
                }
            });

            address.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Double longitude=0.0, latitude=0.0;
                    for(Vendor vendor : temp){
                        if(vendor.getPhone() == phone.getText())
                        {
                            longitude = vendor.getLocation().longitude;
                            latitude = vendor.getLocation().latitude;
                        }
                    }
                    if(longitude != 0.0 && latitude!= 0.0) {
                        String uri = "http://maps.google.com/maps?"+ "&daddr=" + latitude + "," + longitude;
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        c.startActivity(intent);
                    }
                    else{
                        Toast.makeText(itemView.getContext(),"Address not founf",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            myListener.onVendorClick(temp.get(getAdapterPosition()));
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFiler;
    }
    Filter exampleFiler = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Vendor> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(data);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Vendor item : data) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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
        void onVendorClick(Vendor vendor);

    }
}
