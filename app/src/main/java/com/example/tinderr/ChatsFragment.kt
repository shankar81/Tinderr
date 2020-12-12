package com.example.tinderr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var messageRecyclerView: RecyclerView
    private val adapter = MatchAdapter()
    private val messagesAdapter = MessageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chats, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        messageRecyclerView = view.findViewById(R.id.messageRecyclerView)
        messageRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        messageRecyclerView.adapter = messagesAdapter

        return view
    }

    override fun onStart() {
        super.onStart()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    private inner class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    private inner class MatchAdapter : RecyclerView.Adapter<MatchViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
            return MatchViewHolder(layoutInflater.inflate(R.layout.match_list_item, parent, false))
        }

        override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {}
        override fun getItemCount() = 10
    }

    private inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    private inner class MessageAdapter : RecyclerView.Adapter<MessageViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
            return MessageViewHolder(
                layoutInflater.inflate(
                    R.layout.message_list_item,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {}
        override fun getItemCount() = 1
    }
}