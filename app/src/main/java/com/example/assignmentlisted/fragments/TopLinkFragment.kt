package com.example.assignmentlisted.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentlisted.R
import com.example.assignmentlisted.adapters.TopLinksAdapter
import com.example.assignmentlisted.data.ServerResponse
import com.example.assignmentlisted.data.TopLink
import com.example.assignmentlisted.data.repository.MainRepository
import com.example.assignmentlisted.databinding.FragmentLinkBinding
import com.example.assignmentlisted.databinding.TopLinksBinding
import com.example.assignmentlisted.util.ApiState
import com.example.assignmentlisted.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopLinkFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private var binding: TopLinksBinding? = null

    private  var recyclerView: RecyclerView ? = null
    private  var adapter : TopLinksAdapter ? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = TopLinksBinding.inflate(inflater, container, false)

        recyclerView = binding?.rvTopLink
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

                            val topL = it.data.data.top_links
                            viewModel.topLinks.value = topL

                            recyclerView.apply{
                                adapter = TopLinksAdapter(topL)
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






