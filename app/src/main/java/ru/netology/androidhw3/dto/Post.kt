package ru.netology.androidhw3.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likeByMe: Boolean,
    val counter: Int,
    val numberView: Int,
    var repost: Int,



    )
