package com.melodie.parotia.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.melodie.parotia.R
import com.melodie.parotia.databinding.FragmentProfileBinding
import com.melodie.parotia.ui.account.AccountViewModel
import com.melodie.parotia.ui.account.LoginPromptFragment
import com.melodie.parotia.ui.profile.collection.UserCollectionFragment
import com.melodie.parotia.ui.profile.like.UserLikedPhotoFragment
import com.melodie.parotia.ui.profile.photo.UserPhotoFragment
import com.melodie.parotia.util.toInstagram
import com.melodie.parotia.util.toMap
import com.melodie.parotia.util.toTwitter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private val accountViewModel: AccountViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navController = findNavController()
        val currentBackStackEntry = navController.currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle
        savedStateHandle.getLiveData<Boolean>(LoginPromptFragment.LOGIN_SUCCESSFUL)
            .observe(
                currentBackStackEntry,
                Observer {
                    if (!it) {
//                    val startDestination = navController.graph.startDestination
//                    val navOptions = NavOptions.Builder()
//                        .setPopUpTo(startDestination, true)
//                        .build()
//                    navController.navigate(startDestination, null, navOptions)
                        navController.popBackStack()
                    }
                }
            )
    }

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
        accountViewModel.loggedIn.observe(
            viewLifecycleOwner,
            Observer {
                if (it) {
                } else {
                    findNavController().navigate(R.id.navigation_login_prompt)
                }
            }
        )
        accountViewModel.me.observe(
            viewLifecycleOwner,
            Observer { user ->
                if (user != null) {
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
                    binding.pager.adapter = UserPagerAdapter(this, user.username)
                    TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
                        when (position) {
                            0 -> tab.text = "Photos"
                            1 -> tab.text = "Likes"
                            2 -> tab.text = "Collection"
                        }
                    }.attach()
                } else {
                }
            }
        )
    }
}

class UserPagerAdapter(fragment: Fragment, private val username: String) :
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
