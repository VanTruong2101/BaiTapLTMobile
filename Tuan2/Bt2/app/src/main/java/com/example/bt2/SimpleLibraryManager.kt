package com.example.bt2

class SimpleLibraryManager : LibraryManager() {
    override fun addBook(book: Book) { books.add(book) }
    override fun addUser(user: User) { users.add(user) }
    override fun borrowBook(userId: Int, bookId: Int) {
        val user = users.find { it.getId() == userId }
        val book = books.find { it.getId() == bookId }
        if (user != null && book != null) user.borrowBook(book) else throw IllegalArgumentException("Không tìm thấy!")
    }
    override fun displayBookInfo(bookId: Int) {
        val book = books.find { it.getId() == bookId }
        if (book != null) println("Sách: ${book.getTitle()}, Trạng thái: ${book.isBorrowed()}")
    }
}