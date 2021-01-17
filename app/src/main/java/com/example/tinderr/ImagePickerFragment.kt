package com.example.tinderr

import android.content.Intent
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cameraButton.setOnClickListener {
            val action = ImagePickerFragmentDirections.actionImagePickerFragmentToCameraPreviewFragment(args.callbacks)
            findNavController().navigate(action)
        }

        binding.galleryButton.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            startActivityForResult(Intent.createChooser(intent, "Select photos"), 1)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}