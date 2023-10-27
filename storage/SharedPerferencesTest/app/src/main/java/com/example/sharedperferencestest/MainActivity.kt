package com.example.sharedperferencestest

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            val editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
            editor.run {
                putString("name", "Tom")
                putInt("age", 28)
                putBoolean("married", false)
                apply()
            }
        }

        val restoreButton = findViewById<Button>(R.id.restoreButton)
        restoreButton.setOnClickListener {
            val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
            val name = prefs.getString("name", "")
            val age = prefs.getInt("age", 0)
            val married = prefs.getBoolean("married", false)

            Log.d("MainActivity", "name is $name")
            Log.d("MainActivity", "age is $age")
            Log.d("MainActivity", "married is $married")
        }
    }
}
