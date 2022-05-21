package com.yfrite.healthinjoy.auth

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.yfrite.healthinjoy.R
import com.yfrite.healthinjoy.R.color.*
import com.yfrite.healthinjoy.data.UserRepository
import com.yfrite.healthinjoy.data.eaten.Eaten
import com.yfrite.healthinjoy.data.eaten.EatenRepository
import com.yfrite.healthinjoy.data.messages.Message
import com.yfrite.healthinjoy.data.messages.MessagesRepository
import com.yfrite.healthinjoy.databinding.ActivityRegisterBinding
import com.yfrite.healthinjoy.main.MainActivity
import com.yfrite.healthinjoy.util.android.time.worker.AlarmWorker.Companion.CHANNEL_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    lateinit var userRepository: UserRepository
    private var sex = -1
    private var name = ""
    private var weight = ""
    private var height = ""
    private var lifestyle = ""
    private val lifestyles = arrayOf("Низкая", "Средняя", "Высокая")

    private lateinit var binding: ActivityRegisterBinding

    @Inject
    lateinit var eatenRepository: EatenRepository
    @Inject
    lateinit var messagesRepository: MessagesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Creating start data
        setupData()
        createNotificationChannel()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        userRepository = UserRepository(getSharedPreferences("user", MODE_PRIVATE))

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            lifestyles
        )

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        binding.lifestyles.adapter = adapter

        binding.lifestyles.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Display the selected item text on text view
                lifestyle = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        binding.male.setOnClickListener {
            if (sex == 1)
                binding.female.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        green
                    )
                )
            sex = 0
            it.setBackgroundColor(ContextCompat.getColor(this, dark_green))
        }

        binding.female.setOnClickListener {
            if (sex == 0)
                binding.male.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        green
                    )
                )
            sex = 1
            it.setBackgroundColor(ContextCompat.getColor(this, dark_green))
        }

        binding.finish.setOnClickListener {

            name = binding.username.text!!.toString()
            weight = binding.weight.text.toString()
            height = binding.height.text.toString()

            if (name == "") {
                Toast.makeText(this, "Вы не вписали имя", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (weight == "") {
                Toast.makeText(this, "Вы не вписали вес", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (height == "") {
                Toast.makeText(this, "Вы не вписали рост", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (sex == -1) {
                Toast.makeText(this, "Вы не выбрали пол", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            userRepository.sex = sex
            userRepository.name = name
            userRepository.weight = weight.toInt()
            userRepository.height = height.toInt()
            when (lifestyle) {
                "Низкая" -> userRepository.lifestyle = 0
                "Средняя" -> userRepository.lifestyle = 1
                "Высокая" -> userRepository.lifestyle = 2
            }

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setupData(){
        GlobalScope.launch {
            eatenRepository.insert(Eaten())
            messagesRepository.insert(Message(text = "Кто вы?", from = Message.Sender.USER))
            messagesRepository.insert(Message(text = "Привет, я Джой, ваш личный помощник!", from = Message.Sender.JOY))

        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = getString(R.string.notification_channel_name)
        val descriptionText = getString(R.string.notification_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}