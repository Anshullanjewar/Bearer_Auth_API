package com.example.assignmentlisted.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentlisted.R
import com.example.assignmentlisted.adapters.RecentLinkAdapter
import com.example.assignmentlisted.adapters.TopLinksAdapter
import com.example.assignmentlisted.databinding.RecentLinksBinding
import com.example.assignmentlisted.databinding.TopLinksBinding
import com.example.assignmentlisted.util.ApiState
import com.example.assignmentlisted.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecentLinkFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private var binding: RecentLinksBinding? = null
    private  var recyclerView: RecyclerView ? = null
    private  var adapter : RecentLinkAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = RecentLinksBinding.inflate(inflater, container, false)

        recyclerView = binding?.rvRecentLink
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())


        getDetails()

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    private fun getDetails() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {

                viewModel.data.collect {
                    when (it) {
                        is ApiState.Success -> {

                            val recentL = it.data.data.recent_links
                            viewModel.recentLinks.value = recentL

                            recyclerView.apply{
                                adapter = RecentLinkAdapter(recentL)
                                recyclerView?.adapter = adapter                            }

                        }

                        is ApiState.Failure -> {
                            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()

                        }

                        ApiState.Loading -> {
                            Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()

                        }

                    }
                }
            }
        }
    }
}