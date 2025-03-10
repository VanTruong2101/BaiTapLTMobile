package com.example.bt2

class Book(
    id: Int,
    title: String,
    private var isBorrowed: Boolean = false
) : LibraryItem(id, title), Borrowable {
    override fun borrow() {
        if (!isBorrowed) isBorrowed = true else throw IllegalStateException("Sách đã mượn!")
    }
    override fun returnItem() { isBorrowed = false }
    override fun isBorrowed(): Boolean = isBorrowed
}