package com.example.tinderr

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

class ChatsFragment : Fragment() {

    private lateinit var messageTab: TextView
    private lateinit var feedTab: TextView
    private var fragment: Fragment? = null
    private val messageFragment = MessagesFragment()
    private val feedFragment = FeedFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chats, container, false)

        messageTab = view.findViewById(R.id.messageTab)
        feedTab = view.findViewById(R.id.feedTab)

        return view
    }

    override fun onStart() {
        super.onStart()

        fragment = childFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (fragment == null) {
            fragment = Utils.replaceFragment(messageFragment, childFragmentManager)
        }

        messageTab.setOnClickListener {
            val color = ResourcesCompat.getColor(resources, R.color.pink_700, null)
            messageTab.setTextColor(color)
            feedTab.setTextColor(Color.parseColor("#AAAAAA"))
            fragment = Utils.replaceFragment(messageFragment, childFragmentManager)
        }

        feedTab.setOnClickListener {
            val color = ResourcesCompat.getColor(resources, R.color.pink_700, null)
            feedTab.setTextColor(color)
            messageTab.setTextColor(Color.parseColor("#AAAAAA"))
            fragment = Utils.replaceFragment(feedFragment, childFragmentManager)
        }
    }
}