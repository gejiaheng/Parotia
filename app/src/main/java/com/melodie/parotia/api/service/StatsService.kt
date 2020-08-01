package com.melodie.parotia.api.service

import com.melodie.parotia.model.Stats
import retrofit2.http.GET

interface StatsService {

    @GET("/stats/total")
    suspend fun totalStats(): Stats

    @GET("/stats/month")
    suspend fun monthStats(): Stats
}
