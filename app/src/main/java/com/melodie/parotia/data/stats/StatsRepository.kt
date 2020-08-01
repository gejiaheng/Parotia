package com.melodie.parotia.data.stats

import android.content.Context
import com.melodie.parotia.api.service.StatsService
import com.melodie.parotia.model.Stats
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import javax.inject.Inject

class StatsRepository @Inject constructor(
    private val statsService: StatsService,
    @ApplicationContext val context: Context
) {

    companion object {
        const val STATS_FILE = "stats.tmp"
    }

    suspend fun getTotalStatsLocal(): Stats? {
        return withContext(Dispatchers.IO) {
            try {
                val file = File(context.cacheDir, STATS_FILE)
                ObjectInputStream(FileInputStream(file)).use { ois ->
                    ois.readObject() as Stats
                }
            } catch (e: IOException) {
                null
            } catch (e: FileNotFoundException) {
                null
            } catch (e: ClassNotFoundException) {
                null
            }
        }
    }

    suspend fun getTotalStatsRemote(): Stats = statsService.totalStats()

    suspend fun saveTotalStatsRemoteToLocal(stats: Stats) {
        withContext(Dispatchers.IO) {
            try {
                var file = File(context.cacheDir, STATS_FILE)
                if (file == null) {
                    file = File.createTempFile(STATS_FILE, null, context.cacheDir)
                }
                ObjectOutputStream(FileOutputStream(file)).use { oos ->
                    oos.writeObject(stats)
                }
            } catch (e: IOException) {
            } catch (e: FileNotFoundException) {
            } catch (e: ClassNotFoundException) {
            }
        }
    }

    suspend fun getMonthStats(): Stats = statsService.monthStats()
}
