package com.example.tinderr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tinderr.databinding.FragmentImagePickerBinding

class ImagePickerFragment : Fragment() {
    private var _binding: FragmentImagePickerBinding? = null

    private val binding get() = _binding!!

    private val args by navArgs<ImagePickerFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentImagePickerBinding.inflate(inflater, container, false)

        binding.cameraButton.setOnClickListener {
            val action = ImagePickerFragmentDirections.actionImagePickerFragmentToCameraPreviewFragment(args.callbacks)
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}