package com.yfrite.healthinjoy.main.train.physical.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yfrite.healthinjoy.R
import com.yfrite.healthinjoy.databinding.FragmentTrainingRestBinding


class TrainingRestFragment : Fragment() {

    private lateinit var binding: FragmentTrainingRestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTrainingRestBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

}