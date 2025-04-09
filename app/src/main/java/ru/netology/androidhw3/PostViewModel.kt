package ru.netology.androidhw3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.androidhw3.dto.Post

import ru.netology.androidhw3.repository.PostRepositoryInMemoryImpl

private val empty = Post(
    0,
    "",
    "",
    "",
    false,
    0,
    0,
    0
)

class PostViewModel : ViewModel() {
    private val repository = PostRepositoryInMemoryImpl()

    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    fun likePost(id: Long) = repository.like(id)
    fun repostPost(id: Long) = repository.repost(id)
    fun removeById(id: Long) = repository.removeById(id)

    fun changeContentandSave(text: String) {
        edited.value?.let {
            if (it.content != text) {
                repository.save(it.copy(content = text))
            }
        }
        edited.value = empty
    }

    fun edit(post: Post) {

        edited.value = post
    }





    fun cancelEdit() {
        edited.value = empty
    }
    }





