package com.ahmedmatem.android.workmeter.ui.sites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmedmatem.android.workmeter.data.site.local.Site
import com.ahmedmatem.android.workmeter.databinding.SiteListItemBinding

class SiteListAdapter(private val onClickListener: OnClickListener)
    : ListAdapter<Site, SiteListAdapter.SiteViewHolder>(SiteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        return SiteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SiteViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }

    class OnClickListener(val clickListener: (site: Site) -> Unit) {
        fun onClick(site: Site) = clickListener(site)
    }

    class SiteViewHolder(private val binding: SiteListItemBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Site){
            // TODO: bind layout here
        }

        companion object {
            fun from(parent: ViewGroup): SiteViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SiteListItemBinding.inflate(layoutInflater, parent, false)
                return SiteViewHolder(binding)
            }
        }
    }
}

class SiteDiffCallback : DiffUtil.ItemCallback<Site>() {
    override fun areItemsTheSame(oldItem: Site, newItem: Site): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Site, newItem: Site): Boolean {
        return oldItem == newItem
    }
}