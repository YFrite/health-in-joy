package com.yfrite.healthinjoy.main.health.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yfrite.healthinjoy.R
import com.yfrite.healthinjoy.databinding.FragmentAlarmListBinding


class AlarmListFragment : Fragment() {

    private lateinit var binding: FragmentAlarmListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAlarmListBinding.inflate(layoutInflater, container, false)

        return binding.root
    }
}