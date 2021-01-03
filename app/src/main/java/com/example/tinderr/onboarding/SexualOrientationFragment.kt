package com.example.tinderr.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinderr.R
import com.example.tinderr.Utils
import com.example.tinderr.databinding.FragmentSexualOrientationBinding

class SexualOrientationFragment : Fragment() {

    private var _binding: FragmentSexualOrientationBinding? = null

    private val binding
        get() = _binding!!

    private val orientations = arrayListOf(
        "Straight",
        "Gay",
        "Lesbian",
        "Bisexual",
        "Asexual",
        "Demisexual",
        "Pansexial",
        "Queer",
        "Bicurious"
    )
    private val adapter = OrientationAdapter(orientations)

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

    private inner class OrientationHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(label: String) {
            (itemView as TextView).text = label
            itemView.background = Utils.getResFromAttribute(
                requireActivity(),
                R.attr.selectableItemBackground,
                resources
            )
        }
    }

    private inner class OrientationAdapter(private val list: List<String>) :
        RecyclerView.Adapter<OrientationHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrientationHolder {
            return OrientationHolder(
                layoutInflater.inflate(
                    android.R.layout.simple_list_item_1,
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