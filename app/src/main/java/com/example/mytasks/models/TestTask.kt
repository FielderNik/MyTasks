package com.example.mytasks.models

data class TestTask(
    val id: String,
    val text: String,
    val importance: String,
    val done: Boolean,
    val deadline: Int,
    val created_at: Int,
    val updated_at: Int,
)
