package com.example.assignmentlisted.data.network

import com.example.assignmentlisted.data.ServerResponse
import com.github.mikephil.charting.data.ChartData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {


    companion object {
        const val BASE_URL = "https://api.inopenapp.com/api/v1/"
    }

    @GET("dashboardNew")
    suspend fun getDetails(): Response<ServerResponse>


}