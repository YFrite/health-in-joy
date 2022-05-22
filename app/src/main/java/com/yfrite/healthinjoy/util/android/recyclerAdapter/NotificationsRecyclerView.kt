package com.yfrite.healthinjoy.util.android.recyclerAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView
import com.yfrite.healthinjoy.R
import com.yfrite.healthinjoy.data.notifications.Notification
import com.yfrite.healthinjoy.data.notifications.NotificationsRepository
import com.yfrite.healthinjoy.main.health.ui.HealthFragment
import com.yfrite.healthinjoy.main.health.viewModel.HealthViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

class NotificationsRecyclerView(val data: List<Notification> = ArrayList(), val viewModel: HealthViewModel):
    RecyclerView.Adapter<NotificationsRecyclerView.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: MaterialTextView = view.findViewById(R.id.name)
        val description: MaterialTextView = view.findViewById(R.id.description)
        val time: MaterialTextView = view.findViewById(R.id.time)
        val cancel: FloatingActionButton = view.findViewById(R.id.cancel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = data[position]
        holder.name.text = notification.name
        holder.description.text = notification.description

        val time = notification.time.toInt()
        if(time > 60 && time%60 != 0){
            val hours = time/60
            holder.time.text = "Каждые $hours часа и ${time-hours*60} минут"

        } else if(time > 60 && time%60 == 0){
            val hours = time/60
            holder.time.text = "Каждые $hours часа"

        } else{
            holder.time.text = "Каждые $time минут"
        }

        holder.cancel.setOnClickListener {
            WorkManager.getInstance(holder.time.context)
                .cancelWorkById(UUID.fromString(notification.notificationId))

            viewModel.removeNotification(notification.id)
        }
    }

    override fun getItemCount() = data.size

}