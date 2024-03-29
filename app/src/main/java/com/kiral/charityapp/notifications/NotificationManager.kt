package com.kiral.charityapp.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kiral.charityapp.MainActivity
import com.kiral.charityapp.R

fun createNotificationChannel(context: Context, importance: Int, showBadge: Boolean, name: String, description: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = "${context.packageName}-$name"
        val channel = NotificationChannel(channelId, name, importance)
        channel.description = description
        channel.setShowBadge(showBadge)

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}

fun createNotification(
    context: Context,
    notificationId: Int,
    title: String,
    text: String
){
    val channelId = "${context.packageName}-${context.getString(R.string.app_name)}"
    val notificationBuilder = NotificationCompat.Builder(context, channelId).apply {
        setSmallIcon(R.drawable.ic_logo)
        setContentTitle(title)
        setContentText(text)
        priority = NotificationCompat.PRIORITY_DEFAULT
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        setContentIntent(pendingIntent)
    }
    val notificationManager = NotificationManagerCompat.from(context)
    notificationManager.notify(notificationId, notificationBuilder.build())
}

fun invokeSummaryNotification(
    context: Context,
    notificationId: Int,
    value: Double
){
    createNotification(
        context = context,
        notificationId = notificationId,
        title = context.getString(R.string.Notification_SummaryTitle),
        text = context.getString(R.string.Notification_SummaryText, value)
    )
}

fun invokeNoCreditNotification(
    context: Context,
    notificationId: Int,
){
    createNotification(
        context = context,
        notificationId = notificationId,
        title = context.getString(R.string.Notification_NoCreditTitle),
        text = context.getString(R.string.Notification_NoCreditText)
    )
}

fun invokeRegularDonationNotification(
    context: Context,
    notificationId: Int,
    value: Double,
    charityName: String
){
    createNotification(
        context = context,
        notificationId = notificationId,
        title = context.getString(R.string.Notification_RegularDonationTitle),
        text = context.getString(R.string.Notification_RegularDonationText, value, charityName)
    )
}