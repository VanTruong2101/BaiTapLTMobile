
package com.example.bttuan2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etName = findViewById<EditText>(R.id.etName)
        val etAge = findViewById<EditText>(R.id.etAddress)
        val btnCheck = findViewById<Button>(R.id.btnCheck)

        btnCheck.setOnClickListener {
            val name = etName.text.toString()
            val ageStr = etAge.text.toString()

            if (name.isNotEmpty() && ageStr.isNotEmpty()) {
                val age = ageStr.toIntOrNull() ?: 0

                val category = when {
                    age > 65 -> "Người già"
                    age in 6..65 -> "Người lớn"
                    age in 2..6 -> "Trẻ em"
                    else -> "Em bé"
                }

                Toast.makeText(this, "$name, $age tuổi - $category", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}