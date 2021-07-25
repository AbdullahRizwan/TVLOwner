package com.example.tvlonwer.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvlonwer.R
import com.example.tvlonwer.model.Vehicle
import com.example.tvlonwer.view.Adapters.Adapter_ShowCategory
import com.example.tvlonwer.view.Adapters.Adapter_ShowParts
import com.example.tvlonwer.viewModel.ShowCategoryViewModel


class ShowCategory : Fragment(), Adapter_ShowCategory.OnClickListener {
    private lateinit var vehicle:Vehicle
    private var viewModel = ShowCategoryViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fitragment
        var root=inflater.inflate(R.layout.fragment_show_category, container, false)

        vehicle = requireArguments().get("vehicle") as Vehicle

        if (vehicle ==null) {

            Toast.makeText(requireContext(),"Error getting vehicle information", Toast.LENGTH_LONG).show()
            requireActivity().finish()
        }
        viewModel.getCategory(vehicle)
        viewModel.categories.observe(requireActivity(),{
            var recyclerView = root.findViewById<RecyclerView>(R.id.cateogoryReecyclerView)
            var recyclerViewAdapter =
                Adapter_ShowCategory()
            recyclerViewAdapter.setData(viewModel.categories.value,requireContext(),this)
            recyclerView.layoutManager =  LinearLayoutManager(requireContext())
            recyclerView.adapter = recyclerViewAdapter
            var searchView = root.findViewById<SearchView>(R.id.searchCategory)
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

    override fun onCategoryClick(category: String?) {
        val value  = Intent(activity, showParts::class.java)
        value.putExtra("vehicle",vehicle)
        value.putExtra("category",category)
        startActivity(value)
    }

    companion object {

        @JvmStatic
        fun newInstance(vehicle: Vehicle) = ShowCategory().apply {
            arguments = Bundle().apply {
                putSerializable("vehicle", vehicle)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainScreenActivity?)!!
            .setActionBarTitle("Show Vendors")
    }
}