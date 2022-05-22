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
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import com.yfrite.healthinjoy.R
import com.yfrite.healthinjoy.data.notifications.Notification
import com.yfrite.healthinjoy.databinding.BottomSheetAlarmBinding
import com.yfrite.healthinjoy.main.health.viewModel.HealthViewModel
import com.yfrite.healthinjoy.util.android.time.TimeUtil
import com.yfrite.healthinjoy.util.android.time.worker.AlarmWorker
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.util.concurrent.TimeUnit

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

    override fun getTheme() = R.style.BottomSheetDialogTheme

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
        var minutes = 0
        var hours = 0
        binding.ok.setOnClickListener {

            if (minutes == 0 && hours == 0){
                Toast.makeText(requireContext(), "Вы не указали время", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(!checkTextFields()) return@setOnClickListener

            val duration = TimeUtil.minutesByHours(hours) + minutes

            val viewModel = ViewModelProvider(
                requireParentFragment(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            ).get(HealthViewModel::class.java)

            val data = Data.Builder()
            data.putString("name", binding.name.text.toString())
            data.putString("description", binding.description.text.toString())

            val alarmWorkRequest: PeriodicWorkRequest =
                PeriodicWorkRequestBuilder<AlarmWorker>(Duration.ofMinutes(duration.toLong()))
                    .setInitialDelay(Duration.ofMinutes(duration.toLong()))
                    .setInputData(data.build())
                    .build()

            WorkManager.getInstance(requireContext())
                .enqueueUniquePeriodicWork(binding.name.text.toString(), ExistingPeriodicWorkPolicy.REPLACE, alarmWorkRequest)

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
                val tempMinutes = timePicker.minute
                val tempHours = timePicker.hour

                if(tempMinutes >= 15 && tempHours == 0)
                    binding.time.text = "Напоминать каждые $tempMinutes минут"
                else if(tempMinutes == 0 && tempHours != 0)
                    binding.time.text = "Напоминать каждые $tempHours часов"
                else if(tempMinutes != 0 && tempHours != 0)
                    binding.time.text = "Напоминать каждые $tempMinutes минут и $tempHours часов"
                else{
                    Toast.makeText(requireContext(), "Время не может быть меньше 15 минут.", Toast.LENGTH_LONG).show()
                    return@addOnPositiveButtonClickListener
                }

                minutes = tempMinutes
                hours = tempHours
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