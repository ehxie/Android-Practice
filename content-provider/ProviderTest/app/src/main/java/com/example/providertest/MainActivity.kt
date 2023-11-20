package com.example.providertest

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.core.content.contentValuesOf

class MainActivity : ComponentActivity() {
    private var bookId: String? = null
    private val tag = "MainActivity"

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addData = findViewById<Button>(R.id.addData)
        addData.setOnClickListener {
            val uri = Uri.parse("content://com.example.databasetest.provider/book")
            val values = contentValuesOf(
                "name" to "A Clash of Kings",
                "author" to "George Martin",
                "pages" to 1040,
                "price" to 22.85
            )
            val newUri = contentResolver.insert(uri, values)
            bookId = newUri?.pathSegments?.get(1)
        }

        val queryData = findViewById<Button>(R.id.queryData)
        queryData.setOnClickListener {
            val uri = Uri.parse("content://com.example.databasetest.provider/book")
            contentResolver.query(uri, null, null, null, null)?.apply {
                while (moveToNext()) {
                    val name = getString(getColumnIndex("name"))
                    val author = getString(getColumnIndex("author"))
                    val pages = getInt(getColumnIndex("pages"))
                    val price = getDouble(getColumnIndex("price"))
                    Log.d(tag, "book name is $name")
                    Log.d(tag, "book author is $author")
                    Log.d(tag, "book pages is $pages")
                    Log.d(tag, "book price is $price")
                }
                close()
            }
        }

        val updateData = findViewById<Button>(R.id.updateData)
        updateData.setOnClickListener {
            bookId?.let {
                val uri = Uri.parse("content://com.example.databasetest.provider/book/$it")
                val values = contentValuesOf(
                    "name" to "A Storm of Swords",
                    "pages" to 1216,
                    "price" to 16.66
                )
                contentResolver.update(uri, values, null, null)
            }
        }

        val deleteData = findViewById<Button>(R.id.deleteData)
        deleteData.setOnClickListener {
            bookId?.let {
                val uri = Uri.parse("content://com.example.databasetest.provider/book/$it")
                contentResolver.delete(uri, null, null)
            }
        }
    }
}
