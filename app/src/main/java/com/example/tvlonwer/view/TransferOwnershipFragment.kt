package com.example.tvlonwer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
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
class TransferOwnership : Fragment(),  AdapterView.OnItemSelectedListener
{


    private lateinit var emailOwner: TextView
    private lateinit var emailBuyer: EditText
    private lateinit var btn: Button
    private var plateNo = ""
    private var viewModel =TransferOwnerShipViewModel()
    private var plates = ArrayList<String>()
    private var selectedPlate = MutableLiveData<String>()
    private var mSpinnerInitialized = false
    private lateinit var spinner:Spinner
    private lateinit var plateTextView:TextView
    var country = arrayOf("India", "USA", "China", "Japan", "Other")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_transfer_ownership, container, false)
        emailOwner = root.findViewById(R.id.emailOwner)
        emailBuyer = root.findViewById(R.id.buyerEmail)
        emailOwner.setText(Owner.email())
        btn = root.findViewById<Button>(R.id.requestTransfer)
        root.findViewById<Button>(R.id.requestTransfer).setOnClickListener(){
            onclick()
        }
        spinner  = root.findViewById<Spinner>(R.id.dropDownPlate)
        spinner.setOnItemSelectedListener(this)
        viewModel.getPlates()
        viewModel.platesResult.observe(requireActivity(),{
            plates = viewModel.platesResult.value!!
            val aa: ArrayAdapter<*> =
                ArrayAdapter<Any?>(requireContext(), android.R.layout.simple_spinner_item,
                    plates as List<Any?>
                )
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.setAdapter(aa)
        })

        return root
    }


    private fun onclick(){

        if(!emailBuyer.text.contains("@") || emailOwner.text.length <2 ){
            emailBuyer.error = "Incorrect"
        }
        else{
            btn.isEnabled = false
            viewModel.register(emailOwner.text as String,emailBuyer.text.toString(),plateNo)
            viewModel.result.observe(requireActivity(),{
                if(viewModel.result.value == true) {
                    val fragmentManager: FragmentManager? = fragmentManager
                    val fragmentTransaction: FragmentTransaction =
                        fragmentManager!!.beginTransaction()
                    fragmentTransaction.replace(R.id.nav_host_fragment, HomeFragment())
                    fragmentTransaction.addToBackStack(null)
                    btn.isEnabled = true
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
        viewModel.getPlates()
        (activity as MainScreenActivity?)!!
            .setActionBarTitle("Transfer Ownership")
    }

    override fun onItemSelected(arg0: AdapterView<*>?, arg1: View?, position: Int, id: Long) {
        plateNo = plates[position]
    }

    override fun onNothingSelected(arg0: AdapterView<*>?) {
        // TODO Auto-generated method stub
    }


}