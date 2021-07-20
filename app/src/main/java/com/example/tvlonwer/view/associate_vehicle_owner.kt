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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvlonwer.R
import com.example.tvlonwer.model.Vehicle
import com.example.tvlonwer.model.VehicleUser
import com.example.tvlonwer.viewModel.AssociateVehicleViewModel
import com.example.tvlonwer.model.Result


class associate_vehicle_owner : AppCompatActivity(), Adapter_SelectVehicle.OnClickListener {
    private lateinit var viewViewModel: AssociateVehicleViewModel
    private lateinit var m_Text: String
    private lateinit var l_number: String
    var flag = false
    private lateinit var vehcileOwner: VehicleUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_associate_vehicle_owner)

        var recyclerView = findViewById<RecyclerView>(R.id.recyclerViewAssociateVehicle)
        val recyclerViewAdapter =
            Adapter_SelectVehicle()
        viewViewModel = AssociateVehicleViewModel()
        viewViewModel.data.observe(this, Observer {

            recyclerViewAdapter.setData(viewViewModel.data, this.applicationContext, this)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = recyclerViewAdapter
        })

        var searchView = findViewById<SearchView>(R.id.searchVehicle)
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

    override fun onVehicleClick(vehicle: Vehicle) {

        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Enter Kilometers Driven")

        val input = EditText(this)

        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_NUMBER_VARIATION_NORMAL
        builder.setView(input)

        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which -> m_Text = input.text.toString() ; lisence_number(vehicle)})
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()


    }


    fun Back(view: View) {
        finish()
    }

    fun lisence_number(vehicle: Vehicle){
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Enter Lisence Number")

        val input = EditText(this)

        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_NUMBER_VARIATION_NORMAL
        builder.setView(input)

        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which -> l_number = input.text.toString() ; add(vehicle)})
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    fun add(vehicle: Vehicle){
        viewViewModel.addVehicle(m_Text, l_number,vehicle)
        viewViewModel.result.observe(this, Observer {
            val result = it ?: return@Observer
            if (result is Result.Success) {
                Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainScreenActivity::class.java));
            } else {
                Toast.makeText(this, "Vehicle not Added", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
