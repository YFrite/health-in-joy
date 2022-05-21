package com.yfrite.healthinjoy.main.health.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yfrite.healthinjoy.databinding.FragmentAlarmListBinding
import com.yfrite.healthinjoy.main.health.viewModel.HealthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmListFragment : Fragment() {

    private lateinit var binding: FragmentAlarmListBinding
    private val viewModel by viewModels<HealthViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAlarmListBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.notifications.observe(viewLifecycleOwner){
            var str = ""
            for(item in it){
                str += "${item.name}\n"
            }
            binding.list.text = str
        }

    }

//    private fun initAlarmList(){
//        binding.list.item
//    }
}