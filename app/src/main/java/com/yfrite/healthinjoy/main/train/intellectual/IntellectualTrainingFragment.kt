package com.yfrite.healthinjoy.main.train.intellectual

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yfrite.healthinjoy.databinding.FragmentIntellectualTrainingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntellectualTrainingFragment : Fragment() {

    private lateinit var binding: FragmentIntellectualTrainingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentIntellectualTrainingBinding.inflate(inflater, container, false)

        return binding.root.rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.btn.setOnClickListener {
//            Navigation.findNavController(it).popBackStack()
//        }
    }

}