package com.melodie.parotia.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.melodie.parotia.databinding.FragmentProfileBinding
import com.melodie.parotia.model.User
import com.melodie.parotia.ui.profile.collection.UserCollectionFragment
import com.melodie.parotia.ui.profile.like.UserLikedPhotoFragment
import com.melodie.parotia.ui.profile.photo.UserPhotoFragment
import com.melodie.parotia.util.toInstagram
import com.melodie.parotia.util.toMap
import com.melodie.parotia.util.toTwitter

abstract class BaseProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

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

    protected fun bindUser(user: User) {
        binding.user = user
        binding.location.setOnClickListener {
            toMap(requireContext(), user.location)
        }
        binding.twitterBtn.setOnClickListener {
            toTwitter(requireContext(), user.twitter_username)
        }
        binding.insBtn.setOnClickListener {
            toInstagram(requireContext(), user.instagram_username)
        }
        setupPager(user.username)
    }

    private fun setupPager(username: String) {
        binding.pager.adapter = ProfilePagerAdapter(this, username)
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> tab.text = "Photos"
                1 -> tab.text = "Likes"
                2 -> tab.text = "Collection"
            }
        }.attach()
    }
}

class ProfilePagerAdapter(fragment: Fragment, private val username: String) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val args = Bundle()
        args.putString("username", username)
        val frag = when (position) {
            0 -> UserPhotoFragment()
            1 -> UserLikedPhotoFragment()
            2 -> UserCollectionFragment()
            else -> throw IllegalArgumentException("Wrong position argument: $position")
        }
        frag.arguments = args
        return frag
    }
}
