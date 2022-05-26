package com.yfrite.healthinjoy.util.android.recyclerAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.yfrite.healthinjoy.R
import com.yfrite.healthinjoy.data.trainings.Training
import kotlin.collections.ArrayList

class TrainingsRecyclerAdapter(val data: List<Training> = ArrayList()):
    RecyclerView.Adapter<TrainingsRecyclerAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: MaterialTextView = view.findViewById(R.id.name)
        val duration: MaterialTextView = view.findViewById(R.id.duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.training_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val training = data[position]
        holder.name.text = training.name
        holder.duration.text = "${training.time} секунд"
    }

    override fun getItemCount() = data.size

}