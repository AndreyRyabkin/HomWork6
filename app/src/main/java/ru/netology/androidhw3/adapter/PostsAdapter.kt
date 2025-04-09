package ru.netology.androidhw3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.androidhw3.R
import ru.netology.androidhw3.databinding.CardPostBinding
import ru.netology.androidhw3.dto.Post
import ru.netology.androidhw3.format


interface OnInteractionListener{
    fun onLike(post: Post){}
    fun onEdit(post: Post){}
    fun onRemove(post: Post){}
    fun repostPost(post: Post){}
}



class PostsAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Post, PostsViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostsViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener
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
                onInteractionListener.onLike(post)
            }

            imageView.setOnClickListener {
                onInteractionListener.repostPost(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_actions)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
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

