package com.example.tvlonwer.view.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tvlonwer.R
import com.example.tvlonwer.model.VendorParts

class PartsAdapter (var dataSet: List<VendorParts>,
                    private val itemClickListener: PartClickListener
) :
    RecyclerView.Adapter<PartsAdapter.ViewHolder>() {


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        val name: TextView = view.findViewById(R.id.label_name)
        val type: TextView = view.findViewById(R.id.label_type)
        val life: TextView = view.findViewById(R.id.label_life)
        val description: TextView = view.findViewById(R.id.label_description)
        val price: TextView = view.findViewById(R.id.label_price)


        init {
        }
        fun bind(part: VendorParts, position: Int, clickListener: PartClickListener)
        {
            view.setOnClickListener {
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.part_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val part = dataSet[position]
        viewHolder.name.text = part.name
        viewHolder.type.text = part.type
        viewHolder.life.text = part.life
        viewHolder.description.text = part.description
        val priceText = "Rs. ${part.price}"
        viewHolder.price.text = priceText
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
interface PartClickListener {

}