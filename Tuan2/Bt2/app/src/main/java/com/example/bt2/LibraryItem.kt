package com.example.bt2

open class LibraryItem(
    private var id: Int,
    private var title: String
) {
    fun getId(): Int = id
    fun getTitle(): String = title
}