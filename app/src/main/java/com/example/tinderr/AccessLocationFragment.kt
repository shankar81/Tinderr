package com.example.tinderr

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior

private const val LOCATION_PERMISSION = 1
private const val TAG = "AccessLocationFragment"

class AccessLocationFragment : Fragment() {

    private lateinit var bottomSheetButton: TextView
    private lateinit var closeSheetButton: ImageView
    private lateinit var button: Button
    private lateinit var sheetButton: Button
    private lateinit var bottomSheet: ConstraintLayout
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_access_location, container, false)

        bottomSheetButton = view.findViewById(R.id.bottomSheetButton)
        button = view.findViewById(R.id.button)
        sheetButton = view.findViewById(R.id.sheetButton)
        bottomSheet = view.findViewById(R.id.bottomSheet)
        closeSheetButton = view.findViewById(R.id.closeSheetButton)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        return view
    }

    private fun askPermission(permission: String, code: Int) {
        val p = ContextCompat.checkSelfPermission(requireContext(), permission)

        if (p == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), code)
        }
    }

    override fun onStart() {
        super.onStart()

        bottomSheetButton.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        closeSheetButton.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        button.setOnClickListener {
            askPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION)
        }

        sheetButton.setOnClickListener {
            askPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "onRequestPermissionsResult: ")
        when (requestCode) {
            LOCATION_PERMISSION -> {
                var granted = "Permission Denied"
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    granted = "Permission Granted"
                }
                Toast.makeText(context, granted, Toast.LENGTH_SHORT).show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}