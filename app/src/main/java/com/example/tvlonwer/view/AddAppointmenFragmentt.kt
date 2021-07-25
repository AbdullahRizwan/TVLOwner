package com.example.tvlonwer.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import com.example.tvlonwer.CURRENTSELECTEDVEHICLE
import com.example.tvlonwer.R
import com.example.tvlonwer.model.Result
import com.example.tvlonwer.model.Vendor
import com.example.tvlonwer.viewModel.AddAppointmentViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddAppointment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddAppointment : Fragment(), AdapterView.OnItemSelectedListener {
    private var viewModel = AddAppointmentViewModel()
    private lateinit var spinner : Spinner
    private var vendorList = ArrayList<Vendor>()
    private lateinit var selectedVendor : String
    private var db = FirebaseFirestore.getInstance()

    companion object {
        protected var  year = -1
        protected var  month = -1
        protected var  date = -1
        protected var  hour = -1
        protected var  minutes = -1
        var dateUpdated = MutableLiveData<Int>()
        var timeUpdated = MutableLiveData<Int>()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_add_appointment, container, false)

        root.findViewById<TextView>(R.id.selectDate).setOnClickListener{
            showDatePickerDialog(root)
        }

        root.findViewById<TextView>(R.id.selectTime).setOnClickListener{
            showTimePickerDialog(root)
        }

        root.findViewById<Button>(R.id.requestAppointment).setOnClickListener{
            register()
        }

        dateUpdated.observe(requireActivity(),{
            root.findViewById<TextView>(R.id.selectDate).text = "" + date+ "d "+month+ "m " + year+ "y "
        })

        timeUpdated.observe(requireActivity(),{
            root.findViewById<TextView>(R.id.selectTime).text = "" + hour + "h "+minutes +"m"
        })

        spinner  = root.findViewById<Spinner>(R.id.spinnerVendor)
        spinner.setOnItemSelectedListener(this)
        viewModel.getVendords()
        viewModel.vendors.observe(requireActivity(),{
            vendorList = viewModel.vendors.value!!
            var stringList = ArrayList<String>()
            for(item in vendorList){
                stringList.add((item.name.toString()))
            }
            val aa: ArrayAdapter<*> =
                ArrayAdapter<Any?>(requireContext(), android.R.layout.simple_spinner_item,
                    stringList as List<Any?>
                )
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.setAdapter(aa)
        })
        return root
    }

    private fun register() {
        var approved = false
        var ownerId = FirebaseAuth.getInstance().currentUser
        var time = Timestamp(year-1900, month, date, hour, minutes,0,0)
        var uservehicle_Id = CURRENTSELECTEDVEHICLE.getVehicleUser()!!.getId()
        var vendor_Id = selectedVendor
        viewModel.addApointment(approved ,ownerId.uid , time , uservehicle_Id, vendor_Id)
        viewModel.result.observe(requireActivity(),{
            if(viewModel.result.value == true){
                val fragmentManager: FragmentManager? = fragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.nav_host_fragment, ViewAppointments())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
            else{
                Toast.makeText(requireContext(),"Something went wrong", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedVendor = vendorList[position].id.toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    fun showTimePickerDialog(v: View) {
        TimePickerFragment().show(requireActivity().supportFragmentManager, "timePicker")
    }

    fun showDatePickerDialog(v: View) {
        val newFragment = DatePickerFragment()
        newFragment.show(requireActivity().supportFragmentManager, "datePicker")
    }

    class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            // Create a new instance of TimePickerDialog and return it
            return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
        }


        override fun onTimeSet(view: TimePicker, _hourOfDay: Int, _minute: Int) {
            AddAppointment.hour = _hourOfDay
            AddAppointment.minutes = _minute
            timeUpdated.value = (timeUpdated.value)?.plus(1)
        }
    }

    class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            // Use the current date as the default date in the picker
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // Create a new instance of DatePickerDialog and return it
            return DatePickerDialog(requireContext(), this, year, month, day)
        }

        override fun onDateSet(view: DatePicker, _year: Int, _month: Int, _day: Int) {
            year  = _year
            month  = _month
            date  = _day
            dateUpdated.value = (dateUpdated.value)?.plus(1)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainScreenActivity?)!!
            .setActionBarTitle("Add Appointments")
    }

}