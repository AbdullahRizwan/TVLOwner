package com.example.tvlonwer.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvlonwer.R
import com.example.tvlonwer.model.Part
import com.example.tvlonwer.model.Vehicle

class showParts : AppCompatActivity(), Adapter_ShowParts.OnClickListener {
    private lateinit var vehicle: Vehicle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_parts)
        val vehicleFromExtra :Bundle ?=intent.extras
        if (vehicleFromExtra!=null) {
            vehicle = vehicleFromExtra.get("vehicle") as Vehicle
        }
        else{
            Toast.makeText(this,"Error getting vehicle information",Toast.LENGTH_LONG).show()
            finish()
        }
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPart)
        var recyclerViewAdapter = Adapter_ShowParts()
        recyclerViewAdapter.setData(vehicle.parts,this,this)
        recyclerView.layoutManager =  LinearLayoutManager(this)
        recyclerView.adapter = recyclerViewAdapter
        var searchView = findViewById<SearchView>(R.id.searchPartFilters)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                recyclerViewAdapter.filter.filter(s)
                return true
            }
        })
    }

    fun back(view: View) {
        finish();
    }

    override fun onPartClick(part: Part?) {
        Toast.makeText(this,"Heyyyy",Toast.LENGTH_SHORT).show()
            if (part != null) {
                part.remainingLife="0"

            }
    }
}