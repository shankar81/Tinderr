package com.example.tinderr

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tinderr.databinding.FragmentLoaderBinding

class LoaderFragment : Fragment() {

    companion object {
        private var _binding: FragmentLoaderBinding? = null

        private val binding
            get() = _binding!!

        fun show() {
            binding.root.visibility = View.VISIBLE
            binding.root.animate().alpha(1f).setDuration(1000L).start()
        }

        fun hide() {
            binding.root.animate().alpha(0f).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    binding.root.visibility = View.GONE
                }
            }).setDuration(1000L).start()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoaderBinding.inflate(inflater)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}