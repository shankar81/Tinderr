package com.example.tinderr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

class LikeFragment : Fragment() {

    private val feedFragment = MyLikesFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_like, container, false)


        return view
    }

    override fun onStart() {
        super.onStart()

        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, feedFragment)
            .commit()
    }

}