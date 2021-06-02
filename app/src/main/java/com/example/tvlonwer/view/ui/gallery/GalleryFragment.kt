package com.example.tvlonwer.view.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvlonwer.MainActivity
import com.example.tvlonwer.R
import com.example.tvlonwer.model.Vehicle
import com.example.tvlonwer.view.MainScreenActivity
import com.example.tvlonwer.view.adapterSelectVehicle
import com.example.tvlonwer.view.associate_vehicle_owner
import com.example.tvlonwer.viewModel.AssociateVehicleModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GalleryFragment : Fragment() , gallaryRecyclerViewAdapter.OnClickListener{

    private lateinit var galleryViewModel: GalleryViewModel
    private lateinit var btn : FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)

        (activity as MainScreenActivity?)?.getSupportActionBar()?.setTitle("Vehicle Manager")
        btn = root.findViewById(R.id.floatingActionButton)
        btn.setOnClickListener {
            startActivity(Intent(root.context, associate_vehicle_owner::class.java))
        }
        var recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        val recyclerViewAdapter = gallaryRecyclerViewAdapter()
        galleryViewModel = GalleryViewModel()
        galleryViewModel.data.observe(this.requireActivity(), Observer {
            recyclerViewAdapter.setData(galleryViewModel.data, this.context, this)
            recyclerView.layoutManager = LinearLayoutManager(this.context)
            recyclerView.adapter = recyclerViewAdapter
        })

        var searchView = root.findViewById<SearchView>(R.id.searchVehicle)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                recyclerViewAdapter.filter.filter(s)
                return true
            }
        })

        return root
    }

    override fun onVehicleClick(vehicle: Vehicle?) {
        Toast.makeText(this.context,vehicle?.make.toString()+" selected",Toast.LENGTH_SHORT).show()
    }
}