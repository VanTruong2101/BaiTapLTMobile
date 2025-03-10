package com.example.bt2

abstract class LibraryManager {
    protected val books: MutableList<Book> = mutableListOf()
    protected val users: MutableList<User> = mutableListOf()
    abstract fun addBook(book: Book)
    abstract fun addUser(user: User)
    abstract fun borrowBook(userId: Int, bookId: Int)
    abstract fun displayBookInfo(bookId: Int)
}