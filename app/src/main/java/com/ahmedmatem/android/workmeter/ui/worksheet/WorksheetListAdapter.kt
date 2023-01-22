package com.ahmedmatem.android.workmeter.ui.worksheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmedmatem.android.workmeter.data.model.Site
import com.ahmedmatem.android.workmeter.data.model.Worksheet
import com.ahmedmatem.android.workmeter.databinding.SiteListItemBinding
import com.ahmedmatem.android.workmeter.databinding.WorksheetListItemBinding
import com.ahmedmatem.android.workmeter.ui.site.SiteListAdapter

class WorksheetListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Worksheet,WorksheetListAdapter.WorksheetViewHolder>(WorksheetDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorksheetViewHolder {
       return WorksheetViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WorksheetViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener { onClickListener.onClick(item) }
        holder.bind(item)
    }

    class OnClickListener(val clickListener: (worksheet: Worksheet) -> Unit) {
        fun onClick(worksheet: Worksheet) = clickListener(worksheet)
    }

    class WorksheetViewHolder(private val binding: WorksheetListItemBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Worksheet){
            binding.worksheet = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): WorksheetViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WorksheetListItemBinding.inflate(layoutInflater, parent, false)
                return WorksheetViewHolder(binding)
            }
        }
    }
}

class WorksheetDiffCallback : DiffUtil.ItemCallback<Worksheet>() {
    override fun areItemsTheSame(oldItem: Worksheet, newItem: Worksheet): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Worksheet, newItem: Worksheet): Boolean {
        return oldItem == newItem
    }
}