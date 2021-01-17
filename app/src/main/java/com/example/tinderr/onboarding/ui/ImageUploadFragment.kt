package com.example.tinderr.onboarding.ui

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.tinderr.LoaderFragment
import com.example.tinderr.R
import com.example.tinderr.Utils.getFileFromURI
import com.example.tinderr.Utils.notifyUser
import com.example.tinderr.Utils.updateButton
import com.example.tinderr.databinding.FragmentImageUploadBinding
import com.example.tinderr.databinding.ImageUploadPlaceholderBinding
import com.example.tinderr.onboarding.OnBoardingViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val REQUEST_PERMISSIONS = 1

/**
 * This callback is passed to cameraFragment
 * So, when user captures the image it will call
 * @see Callbacks.onImageUpload
 */
interface Callbacks : Serializable {
    fun onImageUpload(file: File?)
}

private const val TAG = "ImageUploadFragment"
private const val GET_IMAGE = 1

class ImageUploadFragment(
    val viewPager: ViewPager2,
    val position: Int,
    val viewModel: OnBoardingViewModel
) : Fragment(), Callbacks {

    private var _binding: FragmentImageUploadBinding? = null

    private val binding get() = _binding!!

    private val images = arrayListOf<File?>()
    private val adapter = ImageAdapter(images)

    val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )

    private lateinit var callbacks: Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callbacks = this
    }

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

        binding.button.updateButton(images.size > 1)

        binding.button.setOnClickListener {
            LoaderFragment.show()
            viewModel.uploadImages(images).observe(viewLifecycleOwner, { response ->
                Log.d(TAG, "onCreateView: $it")
                if (response.data != null && response.result == 1) {
                    when (response.result) {
                        1 -> {
                            notifyUser(requireContext(), "Images are uploaded successfully!! (${response.data.size} Size")
                            LoaderFragment.hide()
                            val action = OnBoardingFragmentDirections.actionOnBoardingFragmentToAccessLocationFragment()
                            findNavController().navigate(action)
                        }
                        0 -> notifyUser(requireContext(), "Uploading failed!")
                    }
                }
            })
        }

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
                    val intent = Intent().apply {
                        type = "image/*"
                        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                        action = Intent.ACTION_GET_CONTENT
                    }
                    startActivityForResult(intent, GET_IMAGE)
//                    val action = OnBoardingFragmentDirections.actionOnBoardingFragmentToImagePickerFragment(callbacks)
//                    findNavController().navigate(action)
                }
            }
        }

        fun bind(imageUrl: File?) {
            val requestBuilder = RequestOptions().transform(RoundedCorners(10))
            Glide.with(requireContext())
                .asBitmap()
                .apply(requestBuilder)
                .load(Uri.parse("/storage/emulated/0/DCIM/2021-01-17-21-42-46-160.jpg"))
                .into(holderBinding.imageView)
        }
    }


    private inner class ImageAdapter(private val images: ArrayList<File?>) :
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
            if (position < images.size) {
                holder.bind(images[position])
            }
        }

        override fun getItemCount() = 6
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

    override fun onImageUpload(file: File?) {
        images.add(file)
        Log.d(TAG, "onImageUpload: $images")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GET_IMAGE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val clipData = data.clipData
                    val intentData = data.data
                    if (clipData != null) {
                        /**
                         * If multiple items are selected this code will be called
                         */
                        val count = if (clipData.itemCount > 6) 6 else clipData.itemCount
                        for (i in 0 until count) {
                            images.add(clipData.getItemAt(i).uri.getFileFromURI(requireContext()))
                        }
                    } else if (intentData != null) {
                        /**
                         * If single item is selected this code will be called
                         */
                        val file = intentData.getFileFromURI(requireContext())
                        images.add(file)
                    }
                } else {
                    Log.d(TAG, "onActivityResult RESULT is not OK OR DATA IS NULL")
                }
                Log.d(TAG, "onActivityResult $images")
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}