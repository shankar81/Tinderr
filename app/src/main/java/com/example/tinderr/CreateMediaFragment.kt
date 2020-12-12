package com.example.tinderr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class CreateMediaFragment : Fragment() {

    private lateinit var backButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_media, container, false)

        backButton = view.findViewById(R.id.backButton)

        return view
    }


    override fun onStart() {
        super.onStart()

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}