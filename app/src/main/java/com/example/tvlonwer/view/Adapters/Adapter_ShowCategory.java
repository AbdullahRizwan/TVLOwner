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

public class Adapter_ShowCategory extends RecyclerView.Adapter<Adapter_ShowCategory.ViewHolder> implements Filterable {
    private ArrayList<String> data;
    protected ArrayList<String> temp;
    private Context c;
    private Adapter_ShowCategory.OnClickListener myListener;

    @Override
    public Adapter_ShowCategory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_category, parent, false);
        return new Adapter_ShowCategory.ViewHolder(view,myListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_ShowCategory.ViewHolder holder, int position) {
        if(temp.size() > position) {
            holder.name.setText(temp.get(position));

        }
        else {
            Toast.makeText(c,"Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }

    public void setData(ArrayList<String> d, Context context, Adapter_ShowCategory.OnClickListener click) {
        data = d;
        temp = new ArrayList<String>();
        temp.addAll(d);
        c = context;
        myListener = click;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        Adapter_ShowCategory.OnClickListener clistener;

        public ViewHolder(@NonNull View itemView, Adapter_ShowCategory.OnClickListener myListener) {
            super(itemView);
            name = itemView.findViewById(R.id.category);
            clistener = myListener;
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            Log.d("TAG", "onClick: " + getAdapterPosition());
            myListener.onCategoryClick(temp.get(getAdapterPosition()));
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFiler;
    }
    Filter exampleFiler = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(data);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (String item : data) {
                    if (item.toLowerCase().contains(filterPattern)) {
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
        void onCategoryClick(String category);
    }

}
