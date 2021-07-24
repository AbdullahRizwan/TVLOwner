package com.example.tvlonwer.view.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvlonwer.R
import com.example.tvlonwer.model.Part
import com.example.tvlonwer.view.Adapters.Adapter_ShowParts

class SlideshowFragment : Fragment() ,
    Adapter_ShowParts.OnClickListener{

    private lateinit var slideshowViewModel: SlideshowViewModel
    private lateinit var data:ArrayList<Part?>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)

        var recyclerView = root.findViewById<RecyclerView>(R.id.expiredParts)
        var recyclerViewAdapter =
            Adapter_ShowParts()
        recyclerViewAdapter.setData(slideshowViewModel.getParts(),requireContext(),this)
        recyclerView.layoutManager =  LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerViewAdapter

        return root
    }


    override fun onPartClick(part: Part?) {
        Toast.makeText(requireContext(),"Heyyyy", Toast.LENGTH_SHORT).show()
        if (part != null) {
            part.remainingLife="0"

        }
    }
}