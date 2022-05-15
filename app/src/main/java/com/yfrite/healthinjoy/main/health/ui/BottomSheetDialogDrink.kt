package com.yfrite.healthinjoy.main.health.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yfrite.healthinjoy.R
import com.yfrite.healthinjoy.databinding.BottomSheetDrinkBinding
import com.yfrite.healthinjoy.main.health.viewModel.HealthViewModel

class BottomSheetDialogDrink: BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDrinkBinding

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetDrinkBinding.inflate(inflater, container, false)
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
                        .copy(water = it.last().water + binding.water.text!!.toString().toInt())
                )
            }

            dismiss()
        }


    }
}