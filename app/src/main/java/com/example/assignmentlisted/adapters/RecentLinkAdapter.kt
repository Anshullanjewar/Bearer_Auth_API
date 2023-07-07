package com.example.assignmentlisted.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentlisted.data.RecentLink
import com.example.assignmentlisted.data.TopLink
import com.example.assignmentlisted.databinding.FirstItemBinding

class RecentLinkAdapter: RecyclerView.Adapter<RecentLinkViewHolder>() {
    private var recentLinks: List<RecentLink> = emptyList()

    fun setRecentLinks(recentLinks: List<RecentLink>) {
        this.recentLinks = recentLinks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentLinkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FirstItemBinding.inflate(inflater, parent, false)
        return RecentLinkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentLinkViewHolder, position: Int) {
        val recentLink = recentLinks[position]
        holder.bind(recentLink)
    }

    override fun getItemCount(): Int {
        return recentLinks.size
    }
}

class RecentLinkViewHolder(private val binding: FirstItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(recentLink: RecentLink) {
        binding.tvLinkName.text = recentLink.title
        binding.tvLinkClick.text = recentLink.total_clicks.toString()
        binding.tvLinkCopy.text = recentLink.smart_link
    }
}
