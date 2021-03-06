package com.example.tvlonwer.view.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvlonwer.R
import com.example.tvlonwer.model.Vehicle
import com.example.tvlonwer.view.*
import com.example.tvlonwer.view.ui.home.HomeFragment
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
        })



        return root
    }

    override fun onVehicleClick(vehicle: Vehicle?) {

        val value  = Intent(activity, showParts::class.java)
        value.putExtra("vehicle",vehicle)
        var fragment = ShowCategory.newInstance(vehicle!!)
        val fragmentManager: FragmentManager? = fragmentManager
        val fragmentTransaction: FragmentTransaction =
            fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
        fragmentTransaction.addToBackStack(null)
        btn.isEnabled = true
        fragmentTransaction.commit()
    }
}