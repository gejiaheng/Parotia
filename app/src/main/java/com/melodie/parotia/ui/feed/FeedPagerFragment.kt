package com.melodie.parotia.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.melodie.parotia.R
import com.melodie.parotia.databinding.FragmentFeedPagerBinding
import com.melodie.parotia.ui.feed.latest.FeedLatestFragment
import com.melodie.parotia.ui.feed.pupular.FeedPopularFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FeedPagerFragment : Fragment() {

    private lateinit var binding: FragmentFeedPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // why create FeedPagerAdapter here instead of declaring it as member:
        // https://stackoverflow.com/questions/57271553/viewpager2-with-fragments-and-jetpack-navigation-restore-fragments-instead-of-r
        binding.pager.adapter = FeedPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            val title = when (position) {
                0 -> getString(R.string.feed_popular_title)
                1 -> getString(R.string.feed_latest_title)
                else -> throw IllegalArgumentException()
            }
            tab.text = title
        }.attach()
    }
}

class FeedPagerAdapter @Inject constructor(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FeedPopularFragment()
            1 -> FeedLatestFragment()
            else -> throw IllegalArgumentException("Illegal position argument")
        }
    }
}
