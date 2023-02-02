package com.ahmedmatem.android.workmeter.ui.worksheet

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmedmatem.android.workmeter.databinding.PhotoListItemBinding
import com.squareup.picasso.Picasso
import java.io.File

data class PhotoData (val id: String, val uri: String)

class PhotoListAdapter (private val clickListener: OnClickListener)
    : ListAdapter<PhotoData,PhotoListAdapter.PhotoViewHolder>(PhotoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener { clickListener.onClick(item) }
        holder.bind(item)
    }

    class OnClickListener(val clickListener: (photo: PhotoData) -> Unit) {
        fun onClick(photo: PhotoData) = clickListener(photo)
    }

    class PhotoViewHolder(private val binding: PhotoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PhotoData) {
            Picasso.get()
                .load(Uri.parse(item.uri))
                .into(binding.photoIv)
        }

        companion object {
            fun from(parent: ViewGroup) : PhotoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PhotoListItemBinding.inflate(layoutInflater, parent, false)
                return PhotoViewHolder(binding)
            }
        }
    }
}

class PhotoDiffCallback : DiffUtil.ItemCallback<PhotoData>() {
    override fun areItemsTheSame(oldItem: PhotoData, newItem: PhotoData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoData, newItem: PhotoData): Boolean {
        return oldItem == newItem
    }
}