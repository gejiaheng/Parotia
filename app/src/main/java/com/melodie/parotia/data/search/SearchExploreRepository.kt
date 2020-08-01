package com.melodie.parotia.data.search

import com.melodie.parotia.R
import com.melodie.parotia.model.SearchExplore
import javax.inject.Inject

class SearchExploreRepository @Inject constructor() {
    suspend fun getSearchExplores(): List<SearchExplore> {
        return listOf(
            SearchExplore("Summer", R.drawable.search_explore_summer, null),
            SearchExplore("Artwork", R.drawable.search_explore_artwork, null),
            SearchExplore("Design", R.drawable.search_explore_design, null),
            SearchExplore("Coffee", R.drawable.search_explore_coffee, null),
            SearchExplore("Tech", R.drawable.search_explore_tech, null)
        )
    }
}
