package com.melodie.parotia.ui.search.history

import androidx.annotation.ColorRes
import com.melodie.parotia.R
import kotlin.random.Random

enum class HistoryColor(@ColorRes val color: Int, @ColorRes val colorBg: Int) {
    COLOR_0(R.color.search_history_color_0, R.color.search_history_color_0_20),
    COLOR_1(R.color.search_history_color_1, R.color.search_history_color_1_20),
    COLOR_2(R.color.search_history_color_2, R.color.search_history_color_2_20),
    COLOR_3(R.color.search_history_color_3, R.color.search_history_color_3_20);

    companion object {
        fun randColor() = HistoryColor.values()[Random.nextInt(4)]
    }
}
