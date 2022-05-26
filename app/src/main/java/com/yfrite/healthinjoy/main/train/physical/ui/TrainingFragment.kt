package com.yfrite.healthinjoy.main.train.physical.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.yfrite.healthinjoy.R
import com.yfrite.healthinjoy.databinding.FragmentTrainingBinding
import com.yfrite.healthinjoy.main.train.physical.viewModel.PhysicalTrainingViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class TrainingFragment: Fragment() {

    private lateinit var binding: FragmentTrainingBinding

    val viewModel by viewModels<PhysicalTrainingViewModel>()

    private val args: TrainingFragmentArgs by navArgs()

    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
        setupButtons()
        setupText()
    }

    private fun setupData(){
        viewModel.trainingsByTypeAndDifficulty(args.type, args.difficulty)
        viewModel.currentTrainingNumber.value = args.number
    }

    private fun setupText(){
        viewModel.trainings.observe(viewLifecycleOwner) {
            val training = it[viewModel.currentTrainingNumber.value!!-1]
            binding.name.text = training.name
            binding.description.text = training.description
            if(viewModel.isLast()){
                val action = TrainingFragmentDirections.actionTrainingFragmentToTrainingFinishFragment()
                Navigation.findNavController(binding.nextTraining).navigate(action)
            }
            timer(training.time.toLong())
        }
    }

    private fun setupButtons(){
        binding.nextTraining.setOnClickListener {
            val action: NavDirections
            if(viewModel.isLast()) {
                viewModel.next()

                action = TrainingFragmentDirections.actionTrainingFragmentSelf(
                    args.type,
                    args.difficulty,
                    number = args.number+1
                )

                Navigation.findNavController(binding.nextTraining).navigate(action)
            } else {
                setupData()
                setupButtons()
                setupText()
            }

            onDestroy()
        }

        binding.pause.setOnClickListener {
            val action = TrainingFragmentDirections.actionTrainingFragmentToTrainingRestFragment(args.type, args.difficulty, args.number)
            onDestroy()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun timer(limit: Long){
        timer = object : CountDownTimer(limit*1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                if(millisUntilFinished > 1000L)
                    binding.timeRemain.text = (millisUntilFinished / 1000).toString()
            }
            override fun onFinish() {
                val action: NavDirections
                if(viewModel.isLast()) {
                    viewModel.next()

                    action = TrainingFragmentDirections.actionTrainingFragmentSelf(
                        args.type,
                        args.difficulty,
                        number = args.number+1
                    )

                    Navigation.findNavController(binding.nextTraining).navigate(action)
                } else {
                    setupData()
                    setupButtons()
                    setupText()
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}