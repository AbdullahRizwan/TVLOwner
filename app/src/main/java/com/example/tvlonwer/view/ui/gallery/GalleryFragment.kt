package com.example.tvlonwer.view.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tvlonwer.MainActivity
import com.example.tvlonwer.R
import com.example.tvlonwer.view.MainScreenActivity
import com.example.tvlonwer.view.associate_vehicle_owner
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GalleryFragment : Fragment() {

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

        return root
    }
}