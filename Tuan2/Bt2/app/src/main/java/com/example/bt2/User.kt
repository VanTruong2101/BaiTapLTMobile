package com.example.bt2

data class User(
    private var id: Int,
    private var name: String,
    private val borrowedBooks: MutableList<Book> = mutableListOf()
) {
    fun getId(): Int = id
    fun getName(): String = name
    fun getBorrowedBooks(): List<Book> = borrowedBooks
    fun borrowBook(book: Book) { book.borrow(); borrowedBooks.add(book) }
    fun returnBook(book: Book) { book.returnItem(); borrowedBooks.remove(book) }
}