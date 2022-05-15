package com.yfrite.healthinjoy.main.joy.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yfrite.healthinjoy.R
import com.yfrite.healthinjoy.data.messages.Message
import com.yfrite.healthinjoy.databinding.FragmentJoyBinding
import com.yfrite.healthinjoy.main.joy.viewModel.JoyViewModel
import com.yfrite.healthinjoy.util.ai.GPT3
import com.yfrite.healthinjoy.util.android.recycler_adapter.MessagesRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JoyFragment: Fragment(R.layout.fragment_joy) {

    private lateinit var binding: FragmentJoyBinding
    private lateinit var adapter: MessagesRecyclerAdapter
    private lateinit var gpt3: GPT3

    private val viewModel by viewModels<JoyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJoyBinding.inflate(inflater, container, false)

        setupAI()
        setupRecyclerView()
        setupButtons()

        return binding.root.rootView
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun setupAI() {
        gpt3 = GPT3()

        viewModel.history.observe(viewLifecycleOwner) {
            if(it.last().from == Message.Sender.USER)
            GlobalScope.launch {
                viewModel.insert(Message(text = gpt3.answer(it), from = Message.Sender.JOY))
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = MessagesRecyclerAdapter()

        binding.messages.layoutManager = LinearLayoutManager(binding.root.context)
        binding.messages.adapter = adapter

        viewModel.history.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @OptIn(DelicateCoroutinesApi::class)
    private fun setupButtons() {
        binding.send.setOnClickListener {
            when {
//                it.last().from == Message.Sender.USER -> {
//                    val toast = Toast(binding.root.context)
//                    toast.duration = Toast.LENGTH_LONG
//                    toast.setText("Дождитесь Джоя!")
//                    toast.show()
//                }
                binding.message.text.toString() == "" -> {
                    val toast = Toast(binding.root.context)
                    toast.duration = Toast.LENGTH_LONG
                    toast.setText("Сообщение не может быть пустым!")
                    toast.show()
                }
//                binding.message.text.toString() == "/clear" -> {
//                    viewModel.clear()
//                    viewModel.insert(Message(text = "Кто вы?", from = Message.Sender.USER))
//                    viewModel.insert(Message(text = "Привет, я Джой, ваш личный помощник!", from = Message.Sender.JOY))
//
//                }
                else -> {
                    viewModel.insert(
                        Message(
                            from = Message.Sender.USER,
                            text = binding.message.text.toString()
                        )
                    )
                    binding.message.text.clear()
                }
            }
        }
    }
}
