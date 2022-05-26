package com.yfrite.healthinjoy.main.health.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yfrite.healthinjoy.R
import com.yfrite.healthinjoy.databinding.BottomSheetDrinkBinding
import com.yfrite.healthinjoy.databinding.BottomSheetEatBinding
import com.yfrite.healthinjoy.main.health.viewModel.HealthViewModel

class BottomSheetDialogEat: BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetEatBinding

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetEatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.ok.setOnClickListener {
            val viewModel = ViewModelProvider(
                requireParentFragment(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            ).get(HealthViewModel::class.java)

            viewModel.eatenHistory.observe(this) {
                viewModel.updateEatenData(
                    it.last()
                        .copy(calories = it.last().calories + binding.kcal.text!!.toString().toInt(),
                              fats = it.last().fats + binding.fats.text!!.toString().toInt(),
                              protein = it.last().protein + binding.protein.text!!.toString().toInt(),
                              carbohydrates = it.last().carbohydrates + binding.carbohydrates.text!!.toString().toInt())
                )
            }

            dismiss()
        }


    }
}