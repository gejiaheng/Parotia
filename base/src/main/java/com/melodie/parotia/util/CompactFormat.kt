package com.melodie.parotia.util

import java.util.TreeMap

object CompactFormat {

    private val suffixes = mapOf<Long, String>(
        1_000L to "k",
        1_000_000L to "M",
        1_000_000_000L to "G",
        1_000_000_000_000L to "T",
        1_000_000_000_000_000L to "P",
        1_000_000_000_000_000_000L to "E"
    ).toSortedMap()

    fun format(value: Long): String {
        // Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1)
        if (value < 0) return "-" + format(-value)
        if (value < 1000) return value.toString()

        val e: Map.Entry<Long, String> = (suffixes as TreeMap).floorEntry(value)
        val divideBy = e.key
        val suffix = e.value
        val truncated =
            value / (divideBy / 10) // the number part of the output times 10
        val hasDecimal =
            truncated < 100 && truncated / 10.0 != (truncated / 10).toDouble()
        return if (hasDecimal) {
            (truncated / 10.0).toString() + suffix
        } else {
            (truncated / 10).toString() + suffix
        }
    }
}
