package com.example.bt2

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextNhanVien: EditText
    private lateinit var btnDoi: Button
    private lateinit var checkBoxSach01: CheckBox
    private lateinit var checkBoxSach02: CheckBox
    private lateinit var btnThem: Button

    private val libraryManager: LibraryManager = SimpleLibraryManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo dữ liệu
        libraryManager.addBook(Book(1, "Sách 01"))
        libraryManager.addBook(Book(2, "Sách 02"))
        libraryManager.addUser(User(1, "Nguyen Van A"))

        // Khởi tạo các thành phần giao diện
        editTextNhanVien = findViewById(R.id.editTextNhanVien)
        btnDoi = findViewById(R.id.btnDoi)
        checkBoxSach01 = findViewById(R.id.checkBoxSach01)
        checkBoxSach02 = findViewById(R.id.checkBoxSach02)
        btnThem = findViewById(R.id.btnThem)

        // Xử lý nút Đổi
        btnDoi.setOnClickListener {
            val newNhanVien = editTextNhanVien.text.toString()
            if (newNhanVien.isNotEmpty()) {
                libraryManager.addUser(User(2, newNhanVien))
                Toast.makeText(this, "Đã đổi nhân viên thành: $newNhanVien", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng nhập tên nhân viên", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý nút Thêm
        btnThem.setOnClickListener {
            var isAnyBookSelected = false
            try {
                if (checkBoxSach01.isChecked) {
                    libraryManager.borrowBook(1, 1) // Người dùng 1 mượn sách 1
                    isAnyBookSelected = true
                }
                if (checkBoxSach02.isChecked) {
                    libraryManager.borrowBook(1, 2) // Người dùng 1 mượn sách 2
                    isAnyBookSelected = true
                }

                if (isAnyBookSelected) {
                    Toast.makeText(this, "Mượn sách thành công!", Toast.LENGTH_SHORT).show()
                    libraryManager.displayBookInfo(1)
                    libraryManager.displayBookInfo(2)
                } else {
                    Toast.makeText(this, "Vui lòng chọn ít nhất một sách để mượn!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}