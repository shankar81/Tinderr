package com.example.tinderr.onboarding

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tinderr.R
import com.example.tinderr.Utils
import com.example.tinderr.Utils.updateButton
import com.example.tinderr.databinding.FragmentSexualOrientationBinding
import com.example.tinderr.databinding.OrientationListItemBinding

private const val TAG = "SexualOrientationFragme"

class SexualOrientationFragment(
    val viewPager: ViewPager2,
    val position: Int,
    val viewModel: OnBoardingViewModel
) : Fragment() {

    private var _binding: FragmentSexualOrientationBinding? = null

    private val binding
        get() = _binding!!

    private val orientations = arrayListOf(
        "Straight",
        "Gay",
        "Lesbian",
        "Asexual",
        "Pansexial",
        "Bisexual",
        "Queer",
        "Demisexual",
        "Bicurious"
    )
    private val adapter = OrientationAdapter(orientations)

    private val selectedOrientations = hashSetOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSexualOrientationBinding.inflate(inflater)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.overScrollMode = View.OVER_SCROLL_IF_CONTENT_SCROLLS
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Utils.viewPagerCallback(viewPager, position, null, requireActivity())

        for (i in viewModel.orientation.split(",")) {
            selectedOrientations.add(i.trim())
        }
        Log.d(TAG, "onViewCreated: ${viewModel.orientation} $selectedOrientations")
        binding.button.setOnClickListener {
            viewModel.updateOrientations(selectedOrientations.joinToString(","))
            viewPager.setCurrentItem(position + 1, true)
        }

    }

    private inner class OrientationHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orientationBinding = OrientationListItemBinding.bind(itemView)

        fun bind(label: String) {
            if (selectedOrientations.find { it == label } != null) {
                orientationBinding.check.visibility = View.VISIBLE
                orientationBinding.label.setTextColor(
                    Utils.getColor(
                        resources,
                        R.color.pink_700
                    )
                )
                selectedOrientations.add(label)
            }

            itemView.setOnClickListener {
                checkIfSelected(label, orientationBinding)
            }

            orientationBinding.label.text = label
        }
    }

    private fun checkIfSelected(label: String, orientationBinding: OrientationListItemBinding) {
        when (selectedOrientations.firstOrNull { it == label }) {
            null -> {
                if (selectedOrientations.size <= 3) {
                    orientationBinding.check.visibility = View.VISIBLE
                    orientationBinding.label.setTextColor(
                        Utils.getColor(
                            resources,
                            R.color.pink_700
                        )
                    )
                    selectedOrientations.add(label.trim())
                }
            }
            else -> {
                orientationBinding.label.setTextColor(Color.parseColor("#555555"))
                orientationBinding.check.visibility = View.INVISIBLE
                selectedOrientations.remove(label)
            }

        }
        binding.button.updateButton(selectedOrientations.size != 0)
    }

    private inner class OrientationAdapter(private val list: List<String>) :
        RecyclerView.Adapter<OrientationHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrientationHolder {
            return OrientationHolder(
                layoutInflater.inflate(
                    R.layout.orientation_list_item,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: OrientationHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount() = list.size
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}