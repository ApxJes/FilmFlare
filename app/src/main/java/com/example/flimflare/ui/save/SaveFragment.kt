package com.example.flimflare.ui.save

import android.content.res.Resources.NotFoundException
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.flimflare.R
import com.example.flimflare.databinding.FragmentSaveBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SaveFragment : Fragment() {

    private var _binding: FragmentSaveBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = requireActivity().findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = ViewPagerAdapter(requireActivity())

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.text = when(position) {
                0 -> "Movie"
                1 -> "TV Show"
                else -> throw NotFoundException("Fragment not found!!")
            }
        }.attach()

    }
}