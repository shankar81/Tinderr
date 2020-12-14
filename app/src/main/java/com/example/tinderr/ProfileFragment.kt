package com.example.tinderr

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout

private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {

    private lateinit var profileImage: ImageView
    private lateinit var mediaButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        profileImage = view.findViewById(R.id.profileImage)
        mediaButton = view.findViewById(R.id.mediaButton)

        return view
    }

    override fun onStart() {
        super.onStart()

        Glide.with(this)
            .asBitmap()
            .centerCrop()
            .circleCrop()
            .load(R.drawable.sample)
            .into(profileImage)

        mediaButton.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToCreateMediaFragment2()
            findNavController().navigate(action)
        }
    }
}