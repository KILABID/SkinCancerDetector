package com.dicoding.asclepius.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.databinding.ListItemBinding
import com.dicoding.asclepius.remote.response.ArticlesItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ListItemAdapter(private var onItemClick: ((ArticlesItem) -> Unit)? = null) :
    ListAdapter<ArticlesItem, ListItemAdapter.ItemViewHolding>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolding {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolding(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolding, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

    class ItemViewHolding(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticlesItem) {
            binding.tvItem.text = item.title
            val publishedDate = parseDate(item.publishedAt)
            binding.releaseDate.text = publishedDate.toString()
            Glide.with(binding.root.context)
                .load(item.urlToImage)
                .circleCrop()
                .into(binding.profileImage)
        }

        private fun parseDate(dateString: String?): Date? {
            if (dateString == null || dateString.isEmpty()) return null
            try {
                val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                return formatter.parse(dateString)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
    }


    fun setOnItemClickListener(listener: (ArticlesItem) -> Unit) {
        onItemClick = listener
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
        }
    }


}