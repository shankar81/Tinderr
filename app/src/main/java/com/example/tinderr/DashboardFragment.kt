package com.example.tinderr

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private const val TAG = "DashboardFragment"

class DashboardFragment : Fragment() {

    private lateinit var dashboardTabs: TabLayout
    private lateinit var dashboardViewPager: ViewPager2

    private val backButtonCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (findNavController().currentDestination?.label != "DashboardFragment") {
                findNavController().navigateUp()
                return
            }

            if (dashboardTabs.selectedTabPosition != 0) {
                dashboardViewPager.currentItem = 0
            } else {
                val alert = AlertDialog.Builder(requireContext())
                    .setTitle("Exit App!")
                    .setMessage("Are you sure you want to exit the App?")
                    .setPositiveButton("Yes") { _, _ -> requireActivity().finish() }
                    .setNegativeButton("No") { _, _ -> }
                    .create()
                alert.show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dashboard_fragment, container, false)

        dashboardTabs = view.findViewById(R.id.dashboardTabs)
        dashboardViewPager = view.findViewById(R.id.dashboardViewPager)

        return view
    }

    override fun onStart() {
        super.onStart()

        requireActivity().onBackPressedDispatcher.addCallback(backButtonCallback)

        dashboardViewPager.adapter = PagerAdapter(this)

        dashboardTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d(TAG, "onTabSelected: ${tab?.position}")
                dashboardViewPager.isUserInputEnabled = tab?.position != 0
                tab?.icon?.setColorFilter(
                    ResourcesCompat.getColor(resources, R.color.pink_700, null),
                    PorterDuff.Mode.SRC_IN
                )
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.icon?.setColorFilter(
                    Color.parseColor("#cccccc"),
                    PorterDuff.Mode.SRC_IN
                )
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        TabLayoutMediator(dashboardTabs, dashboardViewPager) { tab, index ->
            tab.icon = when (index) {
                0 -> ResourcesCompat.getDrawable(resources, R.drawable.ic_fire, null)
                1 -> ResourcesCompat.getDrawable(resources, R.drawable.ic_star, null)
                2 -> ResourcesCompat.getDrawable(resources, R.drawable.ic_chat, null)
                3 -> ResourcesCompat.getDrawable(resources, R.drawable.ic_user, null)
                else -> ResourcesCompat.getDrawable(resources, R.drawable.ic_user, null)
            }
            tab.icon?.setColorFilter(
                Color.parseColor("#cccccc"),
                PorterDuff.Mode.SRC_IN
            )
        }.attach()

    }

    private inner class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount() = 4

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> SwipeFragment()
                1 -> LikeFragment()
                2 -> ChatsFragment()
                3 -> ProfileFragment()
                else -> ProfileFragment()
            }
        }
    }
}