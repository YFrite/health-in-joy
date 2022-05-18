package com.yfrite.healthinjoy.util.android.time.worker

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.yfrite.healthinjoy.R
import com.yfrite.healthinjoy.data.eaten.EatenRepository
import com.yfrite.healthinjoy.main.health.viewModel.HealthViewModel
import javax.inject.Inject

class AlarmWorker(private val context: Context, workerParam: WorkerParameters):
    Worker(context, workerParam) {

    companion object {
        const val CHANNEL_ID = "HIJ_AlarmChannel"
    }

    override fun doWork(): Result {

        val name = inputData.getString("name")
        val description = inputData.getString("description")

        //Toast.makeText(context, "ALARM", Toast.LENGTH_LONG).show()
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_joy)
            .setContentTitle(name)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(context)){
            notify(1, builder.build())
        }
        return Result.success()
    }
}