package com.example.assignmentlisted.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentlisted.R
import com.example.assignmentlisted.adapters.RecentLinkAdapter
import com.example.assignmentlisted.adapters.TopLinkAdapter
import com.example.assignmentlisted.databinding.RecentLinksBinding
import com.example.assignmentlisted.databinding.TopLinksBinding
import com.example.assignmentlisted.vm.MainViewModel


class RecentLinkFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private var _binding: RecentLinksBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RecentLinksBinding.inflate(inflater, container, false)
        val view = binding.root
        val recyclerView: RecyclerView = binding.rvRecentLink
        val adapter = RecentLinkAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.recentLinks.observe(viewLifecycleOwner) { recentLinks ->
            adapter.setRecentLinks(recentLinks)
        }

        return view

}
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}