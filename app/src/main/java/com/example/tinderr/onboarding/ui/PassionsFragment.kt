package com.example.tinderr.onboarding.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tinderr.LoaderFragment
import com.example.tinderr.R
import com.example.tinderr.Utils
import com.example.tinderr.Utils.dpAsPixel
import com.example.tinderr.Utils.updateButton
import com.example.tinderr.databinding.FragmentPassionsBinding
import com.example.tinderr.databinding.PassionListItemBinding
import com.example.tinderr.onboarding.OnBoardingViewModel
import com.example.tinderr.onboarding.models.Passion

private const val TAG = "PassionsFragment"

class PassionsFragment(
    val viewPager: ViewPager2,
    val position: Int,
    val viewModel: OnBoardingViewModel
) : Fragment() {

    private var _binding: FragmentPassionsBinding? = null
    private val binding get() = _binding!!

    private val selectedPassions = arrayListOf<Int>()

    private val passions = arrayListOf<Passion>()
    private val adapter = PassionAdapter(passions)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPassionsBinding.inflate(inflater)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            LoaderFragment.show()
            viewModel.updatePassions(selectedPassions.joinToString(","))
            viewModel.saveDetails(selectedPassions.joinToString(",")).observe(viewLifecycleOwner, { response ->
                if (response.data != null && response.result == 1) {
                    Toast.makeText(
                        requireContext(),
                        "Successfully saved user details",
                        Toast.LENGTH_LONG
                    ).show()
                }
                LoaderFragment.hide()
            })
        }

        LoaderFragment.show()
        viewModel.getPassions().observe(viewLifecycleOwner, { res ->
            if (res.data != null && res.result == 1) {
                passions.addAll(res.data)
            }
            LoaderFragment.hide()
        })
    }

    private inner class PassionHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PassionListItemBinding.bind(itemView)

        fun bind(position: Int) {
            binding.textView1.setOnClickListener {
                updatePassions(binding.textView1, position)
            }
            binding.textView2.setOnClickListener {
                updatePassions(binding.textView2, position + 1)
            }
            binding.textView3.setOnClickListener {
                updatePassions(binding.textView3, position + 2)
            }

            // 3 items in one row
            if (position < passions.size) {
                binding.textView1.text = passions[position].name
            }
            if (position + 1 < passions.size) {
                binding.textView2.text = passions[position + 1].name
            }
            if (position + 2 < passions.size) {
                binding.textView3.text = passions[position + 2].name
            }
        }
    }

    private fun updatePassions(textView: TextView, position: Int) {
        val paddingHorizontal = dpAsPixel(resources, 15)
        val paddingVertical = dpAsPixel(resources, 10)

        if (selectedPassions.firstOrNull { it == passions[position].id } == null && selectedPassions.size < 5) {
            selectedPassions.add(passions[position].id)

            // Update UI
            textView.setTextColor(Utils.getColor(resources, R.color.pink_700))
            textView.background =
                Utils.getDrawable(resources, R.drawable.radio_textview_primary)
        } else if (selectedPassions.size > 0) {
            selectedPassions.remove(passions[position].id)

            // Update UI
            textView.setTextColor(Color.parseColor("#888888"))
            textView.background =
                Utils.getDrawable(resources, R.drawable.radio_textview)
        }
        textView.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)
        binding.button.text = getString(R.string.passions_button, selectedPassions.size)
        binding.button.updateButton(selectedPassions.size == 5)
    }

    private inner class PassionAdapter(private val list: List<Passion>) :
        RecyclerView.Adapter<PassionHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassionHolder {
            return PassionHolder(
                layoutInflater.inflate(
                    R.layout.passion_list_item,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: PassionHolder, position: Int) {
            if (position == 0) {
                holder.bind(position)
            } else {
                holder.bind(position * 3)
            }
        }

        override fun getItemCount(): Int {
            val count = list.size.toFloat() / 3
            return if (count % 3 == 0.0F) {
                count.toInt()
            } else {
                count.toInt() + 1
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}