package com.example.tinderr

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

const val CHANNEL_ID = "tinderr_notification"
class TinderrApplication : Application() {

    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Tinder Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Tinder basic notification channel"
            notificationManager.createNotificationChannel(channel)
        }
    }
}