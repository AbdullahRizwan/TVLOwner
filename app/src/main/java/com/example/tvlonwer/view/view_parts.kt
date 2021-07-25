package com.example.tvlonwer.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvlonwer.R
import com.example.tvlonwer.model.Vehicle
import com.example.tvlonwer.view.Adapters.PartClickListener
import com.example.tvlonwer.view.Adapters.PartsAdapter
import com.example.tvlonwer.viewModel.ViewVendorInventoryViewModel
import java.util.Observer

class view_parts : AppCompatActivity(), PartClickListener {
    private var partsViewModel: ViewVendorInventoryViewModel = ViewVendorInventoryViewModel()
    private var vendorID=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_parts)
        val vehicleFromExtra :Bundle ?=intent.extras
        if (vehicleFromExtra!=null) {
            vendorID = vehicleFromExtra.get("vendorID") as String

        }
        val partsRecyclerView = findViewById<RecyclerView>(R.id.recyclerview_parts)
        partsRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        val partsAdapter = PartsAdapter(emptyList(), this)

        partsRecyclerView.adapter = partsAdapter

        partsViewModel.data.observe(this,  {
            partsAdapter.dataSet = it
            partsAdapter.notifyDataSetChanged()
        })

        partsViewModel.loadParts(vendorID)

    }
}