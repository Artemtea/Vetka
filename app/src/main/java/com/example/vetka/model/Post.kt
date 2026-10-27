package com.example.vetka.model

data class Post(
    val id: String,
    val groupId: String,
    val authorId: String,
    val imagePath: String,
    val caption: String,
)