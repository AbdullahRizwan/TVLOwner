package com.example.tvlonwer.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvlonwer.R
import com.example.tvlonwer.view.Adapters.Adapter_ShowAppointments
import com.example.tvlonwer.viewModel.ViewAppointmentViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewAppointments.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewAppointments : Fragment() {
    var viewModel = ViewAppointmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root =  inflater.inflate(R.layout.fragment_view_appointments, container, false)
        root.findViewById<FloatingActionButton>(R.id.addAppointment).setOnClickListener{
            val fragmentManager: FragmentManager? = fragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.nav_host_fragment, AddAppointment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


        viewModel.getAppointments()
        viewModel.appointments.observe(requireActivity(),{
            viewModel.getVendors(viewModel.appointments.value!!)
            viewModel.vendors.observe(requireActivity(),{

                var recyclerView = root.findViewById<RecyclerView>(R.id.recyclerviewViewappointment)
                val recyclerViewAdapter =
                    Adapter_ShowAppointments()
                recyclerViewAdapter.setData(viewModel.appointments,viewModel.vendors.value!!, requireActivity())
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = recyclerViewAdapter
            })
        })
        
        return root
    }

    override fun onResume() {
        super.onResume()

        (activity as MainScreenActivity?)!!
            .setActionBarTitle("View Appointments")
    }
}