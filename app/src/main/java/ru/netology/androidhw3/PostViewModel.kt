package ru.netology.androidhw3

import androidx.lifecycle.ViewModel

import ru.netology.androidhw3.repository.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel() {
    private val repository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    fun likePost(id: Long) = repository.like(id)
    fun repostPost(id: Long) = repository.repost(id)
}
