package com.yfrite.healthinjoy.main.health.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.yfrite.healthinjoy.databinding.FragmentAlarmListBinding
import com.yfrite.healthinjoy.main.health.viewModel.HealthViewModel
import com.yfrite.healthinjoy.util.android.recyclerAdapter.NotificationsRecyclerAdapter
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

        setupButtons()

        var adapter: NotificationsRecyclerAdapter

        binding.alarms.layoutManager = LinearLayoutManager(binding.root.context)

        viewModel.notifications.observe(viewLifecycleOwner){
            adapter = NotificationsRecyclerAdapter(it, viewModel)
            binding.alarms.adapter = adapter
        }
    }

    private fun setupButtons(){
        binding.back.setOnClickListener {
//            val action = AlarmListFragmentDirections.actionAlarmListFragmentToHealthFragment()
//            Navigation.findNavController(it).navigate(action)
            Navigation.findNavController(it).popBackStack()
        }
    }

//    private fun initAlarmList(){
//        binding.list.item
//    }
}