package com.yfrite.healthinjoy.util.android.recyclerAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yfrite.healthinjoy.R
import com.yfrite.healthinjoy.data.messages.Message

class MessagesRecyclerAdapter:
    RecyclerView.Adapter<MessagesRecyclerAdapter.ViewHolder>(){

    private var data: ArrayList<Message> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(data: List<Message>){
        this.data.clear()
        this.data = data as ArrayList<Message>
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val joyMessage: TextView = view.findViewById(R.id.joy_message)
        val userMessage: TextView = view.findViewById(R.id.user_message)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dialog_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(data[position].from == Message.Sender.USER){
            holder.userMessage.text = data[position].text
            holder.joyMessage.visibility = View.GONE
        } else{
            holder.joyMessage.text = data[position].text
            holder.userMessage.visibility = View.GONE
        }
    }

    override fun getItemCount() = data.size
}