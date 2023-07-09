package com.example.assignmentlisted.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentlisted.R
import com.example.assignmentlisted.data.TopLink

class TopLinksAdapter(private val topZLink :List<TopLink>) :RecyclerView.Adapter<TopLinksAdapter.TopLinkViewHolder>() {

    class TopLinkViewHolder(val view: View):RecyclerView.ViewHolder(view){
       private val title = view.findViewById<TextView>(R.id.tv_linkName)
        private val clickLink = view.findViewById<TextView>(R.id.tv_linkClick)
        private val copyLink = view.findViewById<TextView>(R.id.tv_linkCopy)
        fun bind(topLink: TopLink) {
             title.text = topLink.app
            clickLink.text = topLink.total_clicks.toString()
            copyLink.text = topLink.smart_link


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopLinksAdapter.TopLinkViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.first_item, parent, false)
        return TopLinkViewHolder(v)
    }

    override fun onBindViewHolder(holder: TopLinksAdapter.TopLinkViewHolder, position: Int) {
        holder.bind(topZLink[position])    }

    override fun getItemCount(): Int {
    return topZLink.size
    }
}
