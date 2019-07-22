package com.example.advancenotificationdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.bumptech.glide.request.target.NotificationTarget
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
//            notify1()
//            notify2()
            notify3()
        }
    }

    private fun notify3() {
        // Get the layouts to use in the custom notification
        val notificationLayout = RemoteViews(packageName, R.layout.notification_small)
        val notificationLayoutExpanded = RemoteViews(packageName, R.layout.notification_large)

//        notificationLayoutExpanded.setImageViewResource(
//            R.id.iv_demo,
//            R.mipmap.ic_launcher
//        )

        // Apply the layouts to the notification
        val customNotification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayoutExpanded)
            .build()

        val notificationTarget = NotificationTarget(
            this,
            R.id.iv_demo,
            notificationLayoutExpanded,
            customNotification,
            3
        )

        GlideApp.with(this)
            .asBitmap()
            .load("https://freeiconshop.com/wp-content/uploads/edd/bulb-curvy-flat.png".toUri())
            .into(notificationTarget)

//        GlideApp.with(this).load("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
//            .into(notificationTarget)
        notificationManager.notify(3, customNotification)
    }

    private fun notify2() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(
                NotificationCompat.BigTextStyle().bigText("Much longer text that cannot fit one line... Much longer text that cannot fit one line... Much longer text that cannot fit one line... Much longer text that cannot fit one line...")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        notificationManager.notify(2, builder.build())
    }

    private fun notify1() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
//            .setStyle(
//                NotificationCompat.BigTextStyle().bigText("Much longer text that cannot fit one line... Much longer text that cannot fit one line... Much longer text that cannot fit one line... Much longer text that cannot fit one line...")
//            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(1, builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = CHANNEL_DESCRIPTION
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val CHANNEL_ID = "CHANNEL_ID"
        private const val CHANNEL_NAME = "General"
        private const val CHANNEL_DESCRIPTION = "Description n/a"
    }
}
