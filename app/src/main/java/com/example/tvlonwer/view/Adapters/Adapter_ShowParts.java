package com.example.tvlonwer.view.Adapters;

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
import androidx.recyclerview.widget.RecyclerView;
import com.example.tvlonwer.R;
import com.example.tvlonwer.model.Part;

import java.util.ArrayList;
import java.util.List;

public class Adapter_ShowParts extends RecyclerView.Adapter<Adapter_ShowParts.ViewHolder> implements Filterable {
    private ArrayList<Part> data;
    protected ArrayList<Part> temp;
    private Context c;
    private OnClickListener myListener;

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_show_recycler_view_showparts, parent, false);
        return new ViewHolder(view,myListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(temp.size() > position) {
            holder.name.setText(temp.get(position).getName());
            holder.life.setText(temp.get(position).getRemainingLife());
        }
        else {
            Toast.makeText(c,"Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }

    public void setData(ArrayList<Part> d, Context context, OnClickListener click) {
        data = d;
        temp = new ArrayList<Part>();
        temp.addAll(d);
        c = context;
        myListener = click;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView life;
        OnClickListener clistener;

        public ViewHolder(@NonNull View itemView, OnClickListener myListener) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            life = itemView.findViewById(R.id.life);
            clistener = myListener;
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            Log.d("TAG", "onClick: " + getAdapterPosition());
            myListener.onPartClick(temp.get(getAdapterPosition()));
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFiler;
    }
    Filter exampleFiler = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Part> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(data);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Part item : data) {
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
        void onPartClick(Part part);
    }

}