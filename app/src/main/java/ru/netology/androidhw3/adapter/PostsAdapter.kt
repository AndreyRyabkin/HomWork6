package ru.netology.androidhw3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.androidhw3.R
import ru.netology.androidhw3.databinding.CardPostBinding
import ru.netology.androidhw3.dto.Post
import ru.netology.androidhw3.format

typealias OnLikeListener = (Post) -> Unit
typealias OnRepostListener = (Post) -> Unit

class PostsAdapter(
    private val onLikeListener: OnLikeListener,
    private val onRepostListener: OnRepostListener
) : ListAdapter<Post, PostsViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsViewHolder(binding, onLikeListener, onRepostListener)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostsViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onRepostListener: OnRepostListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content

            likes.text = format(post.counter)
            numberOfVies.text = format(post.numberView)
            textView.text = format(post.repost)

            likesView.setImageResource(
                if (post.likeByMe) R.drawable.like_red else R.drawable.like_svgrepo_com)

            likesView.setOnClickListener {
                onLikeListener(post)
            }

            imageView.setOnClickListener {
                onRepostListener(post)
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}