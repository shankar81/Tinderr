package com.example.tinderr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.tinderr.models.User

class SampleFragment : Fragment() {

    private lateinit var editText: EditText
    private lateinit var textView: TextView
    private lateinit var button: Button
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sample, container, false)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        editText = view.findViewById(R.id.editText)
        textView = view.findViewById(R.id.textView)
        button = view.findViewById(R.id.button)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.user.observe(viewLifecycleOwner, {
            textView.text = "${it.name} ${it.phone} ${it.token}"
        })

        button.setOnClickListener {
            val user = User(5, editText.text.toString(), "Some Phone", "Seom Token")
            mainViewModel.updateValue(user)
        }
    }

}