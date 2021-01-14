package com.example.tinderr.onboarding.ui

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.tinderr.R
import com.example.tinderr.databinding.FragmentImageUploadBinding
import com.example.tinderr.databinding.ImageUploadPlaceholderBinding
import com.example.tinderr.onboarding.OnBoardingViewModel

private const val REQUEST_PERMISSIONS = 1

class ImageUploadFragment(
    val viewPager: ViewPager2,
    val position: Int,
    val viewModel: OnBoardingViewModel
) : Fragment() {

    private var _binding: FragmentImageUploadBinding? = null

    private val binding get() = _binding!!

    private val images = arrayListOf(
        null,
        "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
        null,
        null,
        null,
        null
    )
    private val adapter = ImageAdapter(images)
    val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageUploadBinding.inflate(inflater, container, false)

        // Don't show the dialog initially
        binding.accessDialog.root.visibility = View.GONE

        binding.accessDialog.button.setOnClickListener {
            requestPermission(permissions)

            // Hide the dialog
            binding.accessDialog.root.animate().alpha(0f).setDuration(1000L).setListener(object :
                AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    binding.accessDialog.root.visibility = View.GONE
                }
            }).start()
        }

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    private inner class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val holderBinding = ImageUploadPlaceholderBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                // Check permissions if granted
                val result = checkPermission(permissions)
                if (!result) {
                    binding.accessDialog.root.alpha = 0f
                    binding.accessDialog.root.visibility = View.VISIBLE

                    binding.accessDialog.root.animate().alpha(1f).setDuration(1000L)
                        .setListener(object :
                            AnimatorListenerAdapter() {
                            // Don't do anything, this will override the previous listener if any!
                        }).start()
                } else {
                    // TODO show options to pick image
                    val action = ImageUploadFragmentDirections.actionImageUploadFragmentToImagePickerFragment()
                    findNavController().navigate(action)
                }
            }
        }

        fun bind(imageUrl: String?) {
            Glide.with(requireContext())
                .asBitmap()
                .centerCrop()
                .load(imageUrl)
                .into(holderBinding.imageView)
        }
    }


    private inner class ImageAdapter(private val images: ArrayList<String?>) :
        RecyclerView.Adapter<ImageHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
            return ImageHolder(
                layoutInflater.inflate(
                    R.layout.image_upload_placeholder,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ImageHolder, position: Int) {
            holder.bind(images[position])
        }

        override fun getItemCount() = images.size
    }

    private fun checkPermission(permissions: Array<String>): Boolean {
        for (perm in permissions) {
            if (ContextCompat.checkSelfPermission(requireContext(), perm) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private fun requestPermission(permissions: Array<String>) {
        ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_PERMISSIONS)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}