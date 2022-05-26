package com.yfrite.healthinjoy.main.train.physical.ui

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.yfrite.healthinjoy.R
import com.yfrite.healthinjoy.databinding.FragmentTrainingRestBinding


class TrainingRestFragment : Fragment() {

    private lateinit var binding: FragmentTrainingRestBinding

    private val args: TrainingRestFragmentArgs by navArgs()

    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTrainingRestBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupTimer()
        setupButtons()
    }

    private fun setupButtons(){
        binding.skip.setOnClickListener {
            timer.cancel()
            val action = TrainingRestFragmentDirections.actionTrainingRestFragmentToTrainingFragment(args.type, args.difficulty, number = args.number)
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun setupTimer(){
        timer = object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                if(millisUntilFinished > 1000L)
                    binding.timeRemained.text = (millisUntilFinished / 1000).toString()
            }
            override fun onFinish() {
                val action = TrainingRestFragmentDirections.actionTrainingRestFragmentToTrainingFragment(args.type, args.difficulty, number = args.number)
                Navigation.findNavController(binding.skip).navigate(action)
            }
        }.start()

    }
}