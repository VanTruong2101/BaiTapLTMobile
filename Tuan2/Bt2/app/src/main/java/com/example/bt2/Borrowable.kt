package com.example.bt2

interface Borrowable {
    fun borrow()
    fun returnItem()
    fun isBorrowed(): Boolean
}