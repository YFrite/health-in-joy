package com.yfrite.healthinjoy.main.settings

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.yfrite.healthinjoy.R
import com.yfrite.healthinjoy.data.UserRepository
import com.yfrite.healthinjoy.data.eaten.EatenRepository
import com.yfrite.healthinjoy.data.messages.MessagesRepository
import com.yfrite.healthinjoy.databinding.FragmentSettingsBinding
import com.yfrite.healthinjoy.util.android.time.worker.AlarmWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var userRepository: UserRepository

    @Inject
    lateinit var eatenRepository: EatenRepository
    @Inject
    lateinit var messagesRepository: MessagesRepository


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        userRepository = UserRepository(requireActivity().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE))

        setupUserData()
        setupDevTools()

        return binding.root.rootView
    }

    private fun setupUserData(){
        binding.userName.text = userRepository.name
        binding.weight.text = userRepository.weight.toString()
        binding.height.text = userRepository.height.toString()
        binding.droppedCalories.text = userRepository.droppedCalories.toString()

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun setupDevTools(){
        binding.resetData.setOnClickListener {
            GlobalScope.launch {
                (requireContext().getSystemService(ACTIVITY_SERVICE) as ActivityManager)
                    .clearApplicationUserData()
            }
        }

        binding.createNotificationChannel.setOnClickListener {
            createNotificationChannel()
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = getString(R.string.notification_channel_name)
        val descriptionText = getString(R.string.notification_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(AlarmWorker.CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}