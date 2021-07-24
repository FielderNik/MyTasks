package com.example.mytasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.example.mytasks.forms.MainScreenFragment
import com.example.mytasks.functional.NotifyWork
import com.example.mytasks.functional.NotifyWork.Companion.NOTIFICATION_WORK
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentMainScreen = MainScreenFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .add(R.id.flFragmentContainer, fragmentMainScreen, "MainFragment")
            .addToBackStack("backstack")
            .commit()

/*        val notifyWork = PeriodicWorkRequestBuilder<NotifyWork>(15, TimeUnit.MINUTES, 10, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this).enqueue(notifyWork)*/

        /*val data = Data.Builder().putInt(NotifyWork.NOTIFICATION_ID, 0).build()
        val delay = 40000L
        scheduleNotification(delay, data)*/
    }


    private fun scheduleNotification(delay: Long, data: Data) {
        val notificationWork = OneTimeWorkRequest.Builder(NotifyWork::class.java)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS).setInputData(data).build()

        val instanceWorkManager = WorkManager.getInstance(this)
        instanceWorkManager.beginUniqueWork(NOTIFICATION_WORK, ExistingWorkPolicy.REPLACE, notificationWork).enqueue()
    }
}
