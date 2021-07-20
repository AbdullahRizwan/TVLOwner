package com.example.tvlonwer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tvlonwer.R

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransferOwnership : Fragment() {

    private var param1: String? = null
    private var param2: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_transfer_ownership, container, false)

        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainScreenActivity?)!!
            .setActionBarTitle("Transfer Ownership")
    }


}