package com.melodie.parotia.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.melodie.parotia.R
import com.melodie.parotia.databinding.FragmentProfileBinding
import com.melodie.parotia.ui.profile.collection.UserCollectionFragment
import com.melodie.parotia.ui.profile.like.UserLikedPhotoFragment
import com.melodie.parotia.ui.profile.photo.UserPhotoFragment
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalArgumentException

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.authButton.setOnClickListener { _ ->
            viewModel.startOauth(requireActivity())
        }
        binding.pager.adapter =
            UserPagerAdapter(this)
        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> tab.text = "Photos"
                1 -> tab.text = "Likes"
                2 -> tab.text = "Collection"
            }
        }.attach()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }
}

class UserPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserPhotoFragment()
            1 -> UserLikedPhotoFragment()
            2 -> UserCollectionFragment()
            else -> throw IllegalArgumentException("Wrong position argument: $position")
        }
    }
}
