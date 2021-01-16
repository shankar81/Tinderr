package com.example.tinderr

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.CameraView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tinderr.Utils.copyFileTo
import com.example.tinderr.Utils.notifyUser
import com.example.tinderr.databinding.FragmentCameraPreviewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService

private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
private const val TAG = "CameraPreviewFragment"

class CameraPreviewFragment : Fragment() {

    private var _binding: FragmentCameraPreviewBinding? = null

    private val binding get() = _binding!!

    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val args by navArgs<CameraPreviewFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCameraPreviewBinding.inflate(inflater, container, false)

        startCamera()

        return binding.root
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().apply {
                setSurfaceProvider(binding.cameraPreview.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build()

            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        outputDirectory = getOutputDir()

        binding.captureImage.setOnClickListener {
            takePicture()
        }
    }

    private fun takePicture() {
        val imageCapture = imageCapture ?: return

        val photoFile = File(
            outputDirectory, SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(requireContext()), object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val msg = "Photo capture succeeded"

                val photoName = photoFile.name
                val externalDir = Environment.getExternalStorageDirectory()

                val dest = File(externalDir, photoName)
                coroutineScope.launch {
                    photoFile.copyFileTo(dest)
                }
                args.callbacks.onImageUpload(dest)
                notifyUser(requireContext(), msg)
                Log.d(TAG, msg)
                findNavController().popBackStack(R.id.onBoardingFragment, false)
            }

            override fun onError(exception: ImageCaptureException) {
                exception.printStackTrace()
            }
        })
    }

    private fun getOutputDir(): File {
        val mediaDir = requireContext().externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) {
            mediaDir
        } else {
            requireContext().filesDir
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}