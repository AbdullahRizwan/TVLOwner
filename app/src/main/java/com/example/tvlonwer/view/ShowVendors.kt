package com.example.tvlonwer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvlonwer.R
import com.example.tvlonwer.model.Vendor
import com.example.tvlonwer.view.Adapters.Adapter_Select_Current_Vehicle
import com.example.tvlonwer.view.Adapters.Adapter_ShowVendors
import com.example.tvlonwer.viewModel.ShowVendorsViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ShowVendors.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowVendors : Fragment() ,Adapter_ShowVendors.OnClickListener{
    var viewModel = ShowVendorsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_show_vendors, container, false)
        var recyclerView = root.findViewById<RecyclerView>(R.id.showVendorsRecyclerView)
        val recyclerViewAdapter = Adapter_ShowVendors()
        var prg = root.findViewById<ProgressBar>(R.id.showVendorProgressBar)
        prg.visibility = View.VISIBLE
        viewModel.getVendords()
        viewModel.data.observe(requireActivity(),{
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewAdapter.setData(viewModel.data, requireContext(), this)
            recyclerView.adapter = recyclerViewAdapter
            recyclerView!!.adapter!!.notifyItemRangeInserted(0,viewModel.data.value!!.size)
            recyclerViewAdapter.notifyItemRangeInserted(0,viewModel.data.value!!.size)
            prg.visibility = View.GONE
            var searchView = root.findViewById<SearchView>(R.id.searchVendorView)
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

    override fun onResume() {
        super.onResume()
        (activity as MainScreenActivity?)!!
            .setActionBarTitle("Show Vendors")
    }

    override fun onVendorClick(vendor: Vendor?) {
        Toast.makeText(requireContext(),vendor!!.name,Toast.LENGTH_SHORT).show()
    }



}