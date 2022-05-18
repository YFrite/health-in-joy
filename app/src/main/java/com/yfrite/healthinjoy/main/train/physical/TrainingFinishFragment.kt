package com.yfrite.healthinjoy.main.train.physical

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yfrite.healthinjoy.R
import com.yfrite.healthinjoy.databinding.FragmentTrainingFinishBinding


class TrainingFinishFragment : Fragment() {

    private lateinit var binding: FragmentTrainingFinishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {



        binding = FragmentTrainingFinishBinding.inflate(layoutInflater, container, false)

        return binding.root
    }


}