package com.example.tinderr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

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

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, feedFragment)
            .commit()
    }

}