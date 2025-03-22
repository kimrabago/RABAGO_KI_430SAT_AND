package com.ucb.eldroid.newsflash.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import android.Manifest
import com.ucb.eldroid.newsflash.R
import com.ucb.eldroid.newsflash.view.MainActivity


class NotificationHelper(private val context: Context) {

    companion object {
        private const val CHANNEL_ID = "newsflash_channel"
        private const val CHANNEL_NAME = "NewsFlash Notifications"
        private const val NOTIFICATION_ID = 1
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    fun showNewArticleNotification(articleTitle: String, articleDescription: String, imageRes: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        // Intent to open MainActivity and pass article details
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("title", articleTitle)
            putExtra("description", articleDescription)
            putExtra("imageRes", imageRes)
            putExtra("openDetail", true)
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build notification
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle("News Updated!")
            .setContentText(articleTitle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        // Show notification
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }
}
