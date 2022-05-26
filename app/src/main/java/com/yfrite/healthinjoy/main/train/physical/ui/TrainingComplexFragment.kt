package com.yfrite.healthinjoy.main.train.physical.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yfrite.healthinjoy.databinding.FragmentTrainingComplexBinding
import com.yfrite.healthinjoy.main.train.physical.viewModel.PhysicalTrainingViewModel
import com.yfrite.healthinjoy.util.android.recyclerAdapter.TrainingsRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrainingComplexFragment : Fragment() {

    private lateinit var binding: FragmentTrainingComplexBinding

    private val args: TrainingComplexFragmentArgs by navArgs()

    private val viewModel by viewModels<PhysicalTrainingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingComplexBinding.inflate(layoutInflater, container, false)

        initData()
        setupRV()
        setupObserves()
        setupButtons()

        return binding.root
    }

    private fun initData(){
        viewModel.trainingsByTypeAndDifficulty(args.type, args.difficulty)
        binding.trainingName.text = args.name

    }

    private fun setupRV(){
        binding.trainings.layoutManager = LinearLayoutManager(binding.root.context)
    }

    private fun setupButtons(){
        binding.startComplex.setOnClickListener {
            val action = TrainingComplexFragmentDirections.actionTrainingComplexFragmentToTrainingFragment(args.type, args.difficulty)
            Navigation.findNavController(it).navigate(action)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupObserves(){
        viewModel.trainings.observe(viewLifecycleOwner){
            val adapter = TrainingsRecyclerAdapter(it)
            binding.trainings.adapter = adapter

            var fullDuration = 0.0
            for(item in it){
                fullDuration += item.time
            }
            binding.allTime.text = "${fullDuration/60} Мин."
            binding.allTrainings.text = "${it.size} Тренировки"
        }
    }
}