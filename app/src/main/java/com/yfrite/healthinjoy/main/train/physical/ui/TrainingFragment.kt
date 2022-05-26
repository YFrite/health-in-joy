package com.yfrite.healthinjoy.main.train.physical.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.yfrite.healthinjoy.R
import com.yfrite.healthinjoy.databinding.FragmentTrainingBinding
import com.yfrite.healthinjoy.main.train.physical.viewModel.PhysicalTrainingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TrainingFragment: Fragment() {

    private lateinit var binding: FragmentTrainingBinding

    val viewModel by viewModels<PhysicalTrainingViewModel>()

    private val args: TrainingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupData()
        setupButtons()
        setupText()
        timer(2)
    }

    private fun setupData(){
        viewModel.trainingsByTypeAndDifficulty(args.type, args.difficulty)
    }

    private fun setupText(){
        viewModel.trainings.observe(viewLifecycleOwner) {
            val training = it[viewModel.currentTrainingNumber.value!!-1]
            binding.name.text = training.name
            binding.description.text = training.description
        }

    }

    private fun setupButtons(){
        binding.nextTraining.setOnClickListener {
            val action = TrainingFragmentDirections.actionTrainingFragmentSelf(args.type, args.difficulty)
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun timer(limit: Long){
        object : CountDownTimer(limit*1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                if(millisUntilFinished != 0L)
                    binding.timeRemain.text = (millisUntilFinished / 1000).toString()
            }
            override fun onFinish() {
                val action = TrainingFragmentDirections.actionTrainingFragmentSelf(args.type, args.difficulty)
                Navigation.findNavController(binding.nextTraining).navigate(action)
            }
        }.start()
    }
}