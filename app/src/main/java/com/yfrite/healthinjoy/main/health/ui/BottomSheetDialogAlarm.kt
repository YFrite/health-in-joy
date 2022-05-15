package com.yfrite.healthinjoy.main.health.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import com.yfrite.healthinjoy.data.notifications.Notification
import com.yfrite.healthinjoy.data.notifications.NotificationsDao
import com.yfrite.healthinjoy.data.notifications.NotificationsRepository
import com.yfrite.healthinjoy.databinding.BottomSheetAlarmBinding
import com.yfrite.healthinjoy.main.health.viewModel.HealthViewModel
import com.yfrite.healthinjoy.util.android.time.TimeUtil
import com.yfrite.healthinjoy.util.android.time.worker.AlarmWorker
import com.yfrite.healthinjoy.util.android.time.worker.DayWorker
import dagger.hilt.android.AndroidEntryPoint
import java.sql.Time
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class BottomSheetDialogAlarm: BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetAlarmBinding
    private lateinit var timePicker: MaterialTimePicker

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetAlarmBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPickers()
        setupButtons()
    }

    private fun setupPickers(){
        timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(CLOCK_24H)
            .setHour(23)
            .setMinute(0)
            .setTitleText("Повтор через")
            .build()

    }

    @SuppressLint("SetTextI18n")
    private fun setupButtons(){
        binding.ok.setOnClickListener {

            val duration = TimeUtil.secondsByHours(timePicker.hour) + TimeUtil.secondsByMinutes(timePicker.minute)

            if(!checkTextFields()) return@setOnClickListener

            val viewModel = ViewModelProvider(
                requireParentFragment(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            ).get(HealthViewModel::class.java)

            val alarmWorkRequest: PeriodicWorkRequest =
                PeriodicWorkRequestBuilder<AlarmWorker>(Duration.ofSeconds(duration.toLong()))
                    .build()
            WorkManager.getInstance(requireContext())
                .enqueueUniquePeriodicWork(binding.name.text.toString() ,ExistingPeriodicWorkPolicy.REPLACE, alarmWorkRequest)

            viewModel.newNotification(
                Notification(notificationId = alarmWorkRequest.id.toString(),
                    name = binding.name.text.toString(),
                    description = binding.description.text.toString(),
                    time = duration.toString()
                )
            )

            dismiss()
        }

        binding.timePicker.setOnClickListener {
            timePicker.show(childFragmentManager, "TimePickerAlarm")
            timePicker.addOnPositiveButtonClickListener {
                binding.time.text = "${timePicker.hour}: ${timePicker.minute}"
            }
        }

    }

    private fun checkTextFields(): Boolean{
        if(binding.description.text.toString() == ""){
            Toast.makeText(requireContext(), "Описание не может быть пустым!", Toast.LENGTH_LONG).show()
            return false
        }
        if(binding.name.text.toString() == ""){
            Toast.makeText(requireContext(), "Название не может быть пустым!", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}