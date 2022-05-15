package com.yfrite.healthinjoy.main.train.physical

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.yfrite.healthinjoy.R
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


        // BINDING USAGE EXAMPLE(make toast: context = "EXAMPLE", len = LONG)
        // Мой код стайл можешь чекнуть в main/health/ui/HealthFragment
        binding.bodyAdvanced.setOnClickListener {
            Toast.makeText(context, "EXAMPLE", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }

}