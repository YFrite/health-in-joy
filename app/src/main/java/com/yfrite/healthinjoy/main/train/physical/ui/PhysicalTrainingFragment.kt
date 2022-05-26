package com.yfrite.healthinjoy.main.train.physical.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.yfrite.healthinjoy.databinding.FragmentPhysicalTrainingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhysicalTrainingFragment : Fragment() {

    private lateinit var binding: FragmentPhysicalTrainingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhysicalTrainingBinding.inflate(layoutInflater, container, false)

        binding.bodyBeginner.setOnClickListener {
            val action = PhysicalTrainingFragmentDirections.actionPhysicalTrainingFragmentToTrainingComplexFragment("physical", 0, "ВСЁ ТЕЛО: НАЧИНАЮЩИЙ")
            Navigation.findNavController(it).navigate(action)
        }

        return binding.root
    }

}