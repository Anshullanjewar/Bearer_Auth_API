package com.example.assignmentlisted.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentlisted.R
import com.example.assignmentlisted.data.TopLink
import com.example.assignmentlisted.databinding.FirstItemBinding

class TopLinkAdapter : RecyclerView.Adapter<TopLinkViewHolder>() {
        private var topLinks: List<TopLink> = emptyList()

        fun setTopLinks(topLinks: List<TopLink>) {
            this.topLinks = topLinks
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopLinkViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = FirstItemBinding.inflate(inflater, parent, false)
            return TopLinkViewHolder(binding)
        }

        override fun onBindViewHolder(holder: TopLinkViewHolder, position: Int) {
            val topLink = topLinks[position]
            holder.bind(topLink)
        }

        override fun getItemCount(): Int {
            return topLinks.size
        }
    }

    class TopLinkViewHolder(private val binding: FirstItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(topLink: TopLink) {
            binding.tvLinkName.text = topLink.title
            binding.tvLinkClick.text = topLink.total_clicks.toString()
            binding.tvLinkCopy.text = topLink.smart_link
        }
    }



