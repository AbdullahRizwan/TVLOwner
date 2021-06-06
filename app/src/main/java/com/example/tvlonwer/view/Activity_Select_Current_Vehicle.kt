package com.example.tvlonwer.view

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvlonwer.CURRENTSELECTEDVEHICLE
import com.example.tvlonwer.R
import com.example.tvlonwer.model.Result
import com.example.tvlonwer.model.Vehicle
import com.example.tvlonwer.model.VehicleUser

import com.example.tvlonwer.viewModel.CurrentVehicleViewModel
import com.google.firebase.auth.FirebaseAuth


class Activity_Select_Current_Vehicle : AppCompatActivity(), Adapter_Select_Current_Vehicle.OnClickListener {
    private lateinit var viewViewModel: CurrentVehicleViewModel
    private lateinit var m_Text: String
   private lateinit var l_number: String
    //var flag = false
    private lateinit var vehcileOwner: VehicleUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__select__current__vehicle)

        var recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCurrentVehicle)
        val recyclerViewAdapter = Adapter_Select_Current_Vehicle()
        viewViewModel = CurrentVehicleViewModel()

        viewViewModel.data.observe(this, Observer {

            recyclerViewAdapter.setData(viewViewModel.data, this.applicationContext, this)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = recyclerViewAdapter
        })

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

    override fun onUserVehicleClick(vehicle: VehicleUser?) {



              //  Toast.makeText(this, "Vehicle not Added", Toast.LENGTH_SHORT).show()
        /*val value  = Intent(this, MainScreenActivity::class.java)
        startActivity(value)*/
        if (vehicle != null) {
            CURRENTSELECTEDVEHICLE.setCurrentVehicle(vehicle)
            Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainScreenActivity::class.java));
        }
        else
            Toast.makeText(this, "Vehicle not Added", Toast.LENGTH_SHORT).show()

    }


    fun Back(view: View) {
        finish()
    }






}




