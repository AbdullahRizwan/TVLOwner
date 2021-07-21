package com.example.tvlonwer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.tvlonwer.R
import com.example.tvlonwer.model.Owner
import com.example.tvlonwer.view.ui.home.HomeFragment
import com.example.tvlonwer.viewModel.TransferOwnerShipViewModel

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransferOwnership : Fragment() {


    private lateinit var emailOwner: TextView
    private lateinit var emailBuyer: EditText
    private lateinit var plateNo: EditText
    private var viewModel =TransferOwnerShipViewModel()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_transfer_ownership, container, false)
        emailOwner = root.findViewById(R.id.emailOwner)
        emailBuyer = root.findViewById(R.id.buyerEmail)
        plateNo = root.findViewById(R.id.CarPlateNo)
        emailOwner.setText(Owner.email())
        root.findViewById<Button>(R.id.requestTransfer).setOnClickListener(){
            onclick()
        }
        return root
    }

    private fun onclick(){

        if(!emailOwner.text.contains("@") || emailOwner.text.length <2 ){
            emailOwner.error = "Incorrect"
        }
        else if(plateNo.text.length < 3){
            plateNo.error = "error"
        }
        else{
            viewModel.register(emailOwner.text as String,emailBuyer.text.toString(), plateNo.text.toString())
            viewModel.result.observe(requireActivity(),{
                if(viewModel.result.value == true) {
                    val fragmentManager: FragmentManager? = fragmentManager
                    val fragmentTransaction: FragmentTransaction =
                        fragmentManager!!.beginTransaction()
                    fragmentTransaction.replace(R.id.nav_host_fragment, HomeFragment())
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
                else{
                    Toast.makeText(requireActivity(),"Error Requesting",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainScreenActivity?)!!
            .setActionBarTitle("Transfer Ownership")
    }


}