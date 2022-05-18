package com.yfrite.healthinjoy.main.health.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.yfrite.healthinjoy.main.health.viewModel.HealthViewModel
import com.yfrite.healthinjoy.databinding.FragmentHealthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HealthFragment : Fragment() {

    private lateinit var binding: FragmentHealthBinding
    private val viewModel by viewModels<HealthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHealthBinding.inflate(inflater, container, false)

        viewModel.twoNotifications()

        return binding.root.rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserve()
        setupButtons()
    }

    private fun setupButtons() {
        binding.drink.setOnClickListener {
            val dialog = BottomSheetDialogDrink()
            dialog.show(childFragmentManager, "BottomSheetDrink")
        }
        binding.addAlarm.setOnClickListener {
            val dialog = BottomSheetDialogAlarm()
            dialog.show(childFragmentManager, "BottomSheetAlarm")
            viewModel.twoNotifications()
        }
        binding.alarmList.setOnClickListener {
            val action = HealthFragmentDirections.actionHealthFragmentToAlarmListFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupObserve() {

        viewModel.twoNotifications.observe(viewLifecycleOwner) {
            if (it == null) return@observe

            binding.firstEventName.text = it[0].name
            binding.firstEventTime.text = "Осталось ${it[0].time}"

            if (it.size == 2) {
                binding.secondEventName.text = it[1].name
                binding.secondEventTime.text = "Осталось ${it[1].time}"
            }
        }

        viewModel.eatenHistory.observe(viewLifecycleOwner) {
            binding.userWater.text = it.last().water.toString()
            binding.userCalories.text = it.last().calories.toString()
            binding.userCarbohydrates.text = it.last().carbohydrates.toString()
            binding.userFats.text = it.last().fats.toString()
            binding.userProtein.text = it.last().protein.toString()
        }
    }

}