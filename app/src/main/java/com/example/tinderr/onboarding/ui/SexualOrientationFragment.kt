package com.example.tinderr.onboarding.ui

import android.graphics.Color
import android.os.Bundle
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
import com.example.tinderr.onboarding.OnBoardingViewModel
import com.example.tinderr.onboarding.models.Orientation

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
        Orientation("Straight", false),
        Orientation("Gay", false),
        Orientation("Lesbian", false),
        Orientation("Asexual", false),
        Orientation("Pansexial", false),
        Orientation("Bisexual", false),
        Orientation("Queer", false),
        Orientation("Demisexual", false),
        Orientation("Bicurious", false)
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
        binding.recyclerView.setHasFixedSize(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Utils.viewPagerCallback(viewPager, position, null, requireActivity())

        for (i in viewModel.orientation.split(",")) {
            selectedOrientations.add(i)
            orientations.firstOrNull { it.label == i }?.selected = true
        }
        binding.button.updateButton(selectedOrientations.size != 0)

        binding.button.setOnClickListener {
            viewModel.updateOrientations(selectedOrientations.joinToString(","))
            viewPager.setCurrentItem(position + 1, true)
        }

    }

    private inner class OrientationHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orientationBinding = OrientationListItemBinding.bind(itemView)
        fun bind(orientation: Orientation) {
            itemView.setOnClickListener {
                orientation.selected = !orientation.selected
                checkIfSelected(orientation, orientationBinding)
            }

            orientationBinding.label.text = orientation.label
        }
    }

    private fun checkIfSelected(
        orientation: Orientation,
        orientationBinding: OrientationListItemBinding
    ) {
        when (orientation.selected) {
            true -> {
                if (selectedOrientations.size < 3) {
                    orientationBinding.check.visibility = View.VISIBLE
                    orientationBinding.label.setTextColor(
                        Utils.getColor(
                            resources,
                            R.color.pink_700
                        )
                    )
                    selectedOrientations.add(orientation.label)
                }
            }
            else -> {
                orientationBinding.label.setTextColor(Color.parseColor("#555555"))
                orientationBinding.check.visibility = View.INVISIBLE
                selectedOrientations.remove(orientation.label)
            }

        }
        binding.button.updateButton(selectedOrientations.size != 0)
    }

    private inner class OrientationAdapter(private val list: List<Orientation>) :
        RecyclerView.Adapter<OrientationHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrientationHolder {
            return OrientationHolder(
                layoutInflater.inflate(
                    if (viewType == 0) R.layout.orientation_list_item else R.layout.orientation_list_item_selected,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: OrientationHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount() = list.size

        override fun getItemViewType(position: Int): Int {
            return if (list[position].selected) {
                1
            } else {
                0
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}