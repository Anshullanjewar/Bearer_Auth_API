package com.example.assignmentlisted.vm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentlisted.R
import com.example.assignmentlisted.adapters.TopLinkAdapter
import com.example.assignmentlisted.data.RecentLink
import com.example.assignmentlisted.data.ServerResponse
import com.example.assignmentlisted.data.TopLink
import com.example.assignmentlisted.data.repository.MainRepository
import com.example.assignmentlisted.util.ApiState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) :ViewModel() {

    val data = mainRepository.getDetails()
    val overallUrlChart = MutableLiveData<Map<String, Int>>()
     val topLinks = MutableLiveData<List<TopLink>>()
    val recentLinks = MutableLiveData<List<RecentLink>>()



}