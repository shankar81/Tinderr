package com.example.tinderr.auth.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinderr.R
import com.example.tinderr.Utils
import com.example.tinderr.models.CountryCode

private const val TAG = "NumberExtListFragment"

class NumberExtListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: EditText
    private lateinit var backButton: ImageView
    private val numberExtList = Utils.getCountryCodes()
    private val numberExtListFiltered = Utils.getCountryCodes()
    private var adapter = ExtAdapter(numberExtListFiltered)
    val args: NumberExtListFragmentArgs by navArgs()

    val listFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = arrayListOf<CountryCode>()
            if (!constraint.isNullOrBlank()) {
                for (i in numberExtList) {
                    if (i.name.contains(
                            constraint,
                            true
                        ) || i.code.contains(constraint, true)
                    ) {
                        results.add(i)
                    }
                }
            } else {
                results.addAll(numberExtList)
            }

            val filterResult = FilterResults()
            filterResult.values = results
            return filterResult
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            numberExtListFiltered.clear()
            if (results != null) {
                numberExtListFiltered.addAll(results.values as ArrayList<CountryCode>)
                adapter.notifyDataSetChanged()
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_number_ext_list, container, false)

        backButton = view.findViewById(R.id.backButton)
        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.searchView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        return view
    }

    override fun onStart() {
        super.onStart()
        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                listFilter.filter(s)
            }
        })

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private inner class ExtHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView = itemView.findViewById(R.id.name)
        private val number: TextView = itemView.findViewById(R.id.number)

        fun bind(code: CountryCode) {
            itemView.setOnClickListener {
                args.callback?.onSelectExt(code)
                findNavController().navigateUp()
            }
            name.text = code.name
            number.text = code.dialCode
        }
    }

    private inner class ExtAdapter(private val extList: ArrayList<CountryCode>) :
        RecyclerView.Adapter<ExtHolder>(), Filterable {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtHolder {
            return ExtHolder(layoutInflater.inflate(R.layout.number_ext_list_item, parent, false))
        }

        override fun onBindViewHolder(holder: ExtHolder, position: Int) {
            holder.bind(extList[position])
        }

        override fun getItemCount() = extList.size

        override fun getFilter() = listFilter
    }
}