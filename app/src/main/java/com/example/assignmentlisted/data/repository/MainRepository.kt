package com.example.assignmentlisted.data.repository

import com.example.assignmentlisted.data.network.ApiService
import com.example.assignmentlisted.util.toResultFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {
    fun getDetails() = toResultFlow {
        apiService.getDetails()

}
}