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
import com.example.assignmentlisted.adapters.TopLinkAdapter
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
    private var _binding: TopLinksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = TopLinksBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerView: RecyclerView = binding.rvTopLink
        val adapter = TopLinkAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.topLinks.observe(viewLifecycleOwner) { topLinks ->
            adapter.setTopLinks(topLinks)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}






