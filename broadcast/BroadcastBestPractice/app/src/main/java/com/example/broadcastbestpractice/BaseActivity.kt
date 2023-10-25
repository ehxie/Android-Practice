package com.example.broadcastbestpractice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    private lateinit var receiver: ForceOfflineReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityController.addActivity(this)
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.broadcastbestpractice.FORCE_OFFLINE")
        receiver = ForceOfflineReceiver()
        registerReceiver(receiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityController.removeActivity(this)
    }

    inner class ForceOfflineReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            AlertDialog.Builder(context).apply {
                setTitle("Warning")
                setMessage("You are forced to be offline. Please try to login again")
                setPositiveButton("OK") {_,_ ->
                    ActivityController.finishAll()
                    val i = Intent(context, LoginActivity::class.java)
                    context.startActivity(i)
                }
                show()
            }
        }

    }
}