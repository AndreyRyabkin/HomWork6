package ru.netology.androidhw3.repository

import androidx.lifecycle.LiveData
import ru.netology.androidhw3.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun get(id: Long): LiveData<Post>
    fun like(id: Long)
    fun repost(id: Long)
    fun removeById(id: Long)
    fun save (post: Post)

}