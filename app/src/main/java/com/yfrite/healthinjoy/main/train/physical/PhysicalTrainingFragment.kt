package com.yfrite.healthinjoy.main.train.physical

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yfrite.healthinjoy.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhysicalTrainingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_physical_training, container, false)
    }

}