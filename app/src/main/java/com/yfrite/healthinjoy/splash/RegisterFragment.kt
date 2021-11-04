package com.yfrite.healthinjoy.splash

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.yfrite.healthinjoy.data.PreferenceRepository


class RegisterFragment : Fragment() {

    lateinit var preferenceRepository: PreferenceRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        preferenceRepository.userName = "fack"
        Log.e("x", preferenceRepository.userName)
    }
}