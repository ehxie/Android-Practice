package com.example.databasebestpractice

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = MyDatabase(this, "Book", 1)

        val createDatabase = findViewById<Button>(R.id.createDatabase)
        createDatabase.setOnClickListener {
            dbHelper.writableDatabase
        }

        val addData = findViewById<Button>(R.id.addData)
        addData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val value = ContentValues().apply {
                put("name", "hhh")
                put("author", "xxx")
                put("pages", 2300)
                put("price", 16.66)
            }
            db.insert("book", null, value)
        }

        val replaceData = findViewById<Button>(R.id.replaceData)
        replaceData.setOnClickListener {
            val db = dbHelper.writableDatabase
            // 开始事务
            db.beginTransaction()
            try {
                db.delete("Book", null, null)
                if (true) {
                    // 手动抛出异常
                    throw NullPointerException()
                    val value = ContentValues().apply {
                        put("name", "Game of Thrones")
                        put("author", "hh")
                        put("pages", 720)
                        put("price", 20.82)
                    }
                }
            } catch (e: Exception) {
                db.endTransaction()
            }
        }
    }
}