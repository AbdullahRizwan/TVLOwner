package com.example.tvlonwer.view.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvlonwer.R;
import com.example.tvlonwer.model.Appointment;
import com.example.tvlonwer.model.Vendor;

import java.util.ArrayList;

public class Adapter_ShowAppointments extends RecyclerView.Adapter<Adapter_ShowAppointments.ViewHolder>{
    private ArrayList<Appointment> appointments;
    private ArrayList<Vendor> vendorArrayList;
    private Context c;

    @Override
    public Adapter_ShowAppointments.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_view_appointment, parent, false);
        return new Adapter_ShowAppointments.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_ShowAppointments.ViewHolder holder, int position) {
        if(appointments.size() > position) {
            holder.dateandtime.setText(appointments.get(position).getTimestamp().toDate().toString() );
            for(Vendor v: vendorArrayList){
                if((appointments.get(position).getVendor_id().contains(v.getId()))){
                    holder.phone.setText(v.getPhone());
                    holder.name.setText(v.getName());
                    break;
                }
            }
        }
        else {
            Toast.makeText(c,"Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public void setData(MutableLiveData<ArrayList<Appointment>> d, ArrayList<Vendor> vendors, Context context) {
        appointments = d.getValue();
        vendorArrayList = vendors;
        c = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView phone;
        TextView dateandtime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameofvendor);
            phone = itemView.findViewById(R.id.phone);
            dateandtime = itemView.findViewById(R.id.dateandtime);
            Button btn = (Button)itemView.findViewById(R.id.showLocation);

            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Double longitude=0.0, latitude=0.0;
                    for(Vendor vendor:vendorArrayList){
                        if(name.getText().equals(vendor.getName())){
                            longitude = vendor.getLocation().longitude;
                            latitude = vendor.getLocation().latitude;
                            break;
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

    }


}