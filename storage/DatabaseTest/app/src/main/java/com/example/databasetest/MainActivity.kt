package com.example.databasetest

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = MyDatabaseHelper(this, "BookStore.db", 2)
        val createDatabase = findViewById<Button>(R.id.createDataBase)

        createDatabase.setOnClickListener {
            dbHelper.writableDatabase
        }

        val addData = findViewById<Button>(R.id.addData)
        addData.setOnClickListener {
            val db = dbHelper.writableDatabase

            val values1 = ContentValues().apply {
                put("name", "The Da Vinci Code")
                put("author", "Dan")
                put("pages", 454)
                put("price", 16.96)
            }
            db.insert("Book", null, values1)

            val values2 = ContentValues().apply {
                // id 会自动生成
                put("name", "The Da Vinci Code")
                put("author", "Dan")
                put("pages", 550)
                put("price", 16.96)
            }
            db.insert("Book", null, values2)
        }

        val updateData = findViewById<Button>(R.id.updateData)
        updateData.setOnClickListener {
            val db = dbHelper.writableDatabase

            val values = ContentValues()
            values.put("price", 10.00)
            db.update("Book", values, "name = ?", arrayOf("The Da Vinci Code"))
        }

        val deleteData = findViewById<Button>(R.id.deleteData)
        deleteData.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.delete("Book", "pages > ?", arrayOf("500"))
        }

        val queryData = findViewById<Button>(R.id.queryData)
        queryData.setOnClickListener {
            val db = dbHelper.writableDatabase
            // 查询 Book 中所有的数据
            val cursor = db.query("Book", null, null, null, null, null, null)
            if(cursor.moveToFirst()){
                do {
                    // 遍历 cursor 对象，取出数据并打印
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val author = cursor.getString(cursor.getColumnIndex("author"))
                    val pages = cursor.getString(cursor.getColumnIndex("pages"))
                    val price = cursor.getString(cursor.getColumnIndex("price"))

                    Log.d("MainActivity", "book name is $name")
                    Log.d("MainActivity", "book author is $author")
                    Log.d("MainActivity", "book pages is $pages")
                    Log.d("MainActivity", "book price is $price")
                } while (cursor.moveToNext())
                cursor.close()
            }
        }
    }
}
