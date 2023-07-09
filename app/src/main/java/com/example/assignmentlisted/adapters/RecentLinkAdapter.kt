package com.example.assignmentlisted.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentlisted.R
import com.example.assignmentlisted.data.RecentLink
import com.example.assignmentlisted.data.TopLink
import com.example.assignmentlisted.databinding.FirstItemBinding

class RecentLinkAdapter(private val recentZLink :List<RecentLink>) :RecyclerView.Adapter<RecentLinkAdapter.RecentLinkViewHolder>() {

    class RecentLinkViewHolder(val view: View):RecyclerView.ViewHolder(view){
        private val title = view.findViewById<TextView>(R.id.tv_linkName)
        private val clickLink = view.findViewById<TextView>(R.id.tv_linkClick)
        private val copyLink = view.findViewById<TextView>(R.id.tv_linkCopy)
        fun bind(recentLink: RecentLink) {
            title.text = recentLink.app
            clickLink.text = recentLink.total_clicks.toString()
            copyLink.text = recentLink.smart_link


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentLinkAdapter.RecentLinkViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.first_item, parent, false)
        return RecentLinkAdapter.RecentLinkViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecentLinkAdapter.RecentLinkViewHolder, position: Int) {
        holder.bind(recentZLink[position])    }

    override fun getItemCount(): Int {
        return recentZLink.size
    }
}
