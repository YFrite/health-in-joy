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

        setupObserves()
        setupButtons()

        return binding.root.rootView

    }

    override fun onResume() {
        super.onResume()
        setupObserves()
    }

    private fun setupButtons() {
        binding.drink.setOnClickListener {
            val dialog = BottomSheetDialogDrink()
            dialog.show(childFragmentManager, "BottomSheetDrink")
        }
        binding.addAlarm.setOnClickListener {
            val dialog = BottomSheetDialogAlarm()
            dialog.show(childFragmentManager, "BottomSheetAlarm")
        }
        binding.eat.setOnClickListener {
            val dialog = BottomSheetDialogEat()
            dialog.show(childFragmentManager, "BottomSheetEat")
        }
        binding.alarmList.setOnClickListener {
            val action = HealthFragmentDirections.actionHealthFragmentToAlarmListFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupObserves() {

        viewModel.twoNotifications.observe(viewLifecycleOwner) {
            if(it.isEmpty()){
                binding.firstEventName.text = "????????????????????!"
                binding.firstEventTime.text = ""

                binding.secondEventName.text = ""
                binding.secondEventTime.text = ""

                binding.alarmList.visibility = View.GONE
                return@observe
            }

            val time = it[0].time.toInt()

            val timeInText: String = if(time > 60 && time%60 != 0){
                val hours = time/60
                "???????????? $hours ???????? ?? ${time-hours*60} ??????????"
            } else if(time > 60 && time%60 == 0){
                val hours = time/60
                "???????????? $hours ????????"
            } else{
                "???????????? $time ??????????"
            }

            binding.firstEventName.text = it[0].name
            binding.firstEventTime.text = timeInText
            binding.alarmList.visibility = View.VISIBLE
            if (it.size == 2) {
                binding.secondEventName.text = it[1].name
                binding.secondEventTime.text = timeInText
            } else{
                binding.secondEventName.text = ""
                binding.secondEventTime.text = ""
            }
        }

        viewModel.eatenHistory.observe(viewLifecycleOwner) {
            binding.userWater.text = "${it.last().water} ????"
            binding.userCalories.text = "${it.last().calories} ????????"
            binding.userCarbohydrates.text = "${it.last().carbohydrates} ??"
            binding.userFats.text = "${it.last().fats} ??"
            binding.userProtein.text = "${it.last().protein} ??"
        }
    }

}