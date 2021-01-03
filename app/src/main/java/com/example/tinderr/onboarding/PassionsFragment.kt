package com.example.tinderr.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tinderr.R
import com.example.tinderr.databinding.FragmentPassionsBinding
import com.example.tinderr.databinding.PassionListItemBinding

private const val TAG = "PassionsFragment"

class PassionsFragment(val viewPager: ViewPager2, val position: Int) : Fragment() {

    private var _binding: FragmentPassionsBinding? = null
    private val binding get() = _binding!!

    private val passions = arrayListOf(
        "Slam Poetry",
        "Comedy",
        "Bhangra",
        "Disney",
        "Cricket",
        "Bollywood",
        "Photography",
        "Outdoors",
        "VR room",
        "Writer",
        "Coffee",
        "Blogging",
        "Reading",
        "Politics",
        "Museum",
        "Vegan",
        "Cat Lover",
        "Hoodie",
    )
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

    private inner class PassionHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PassionListItemBinding.bind(itemView)

        fun bind(position: Int) {
            if (position < passions.size) {
                binding.textView1.text = passions[position]
            }
            if (position + 1 < passions.size) {
                binding.textView2.text = passions[position + 1]
            }
            if (position + 2 < passions.size) {
                binding.textView3.text = passions[position + 2]
            }
        }
    }

    private inner class PassionAdapter(private val list: List<String>) :
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