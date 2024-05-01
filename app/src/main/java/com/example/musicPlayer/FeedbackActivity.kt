package com.example.musicPlayer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicPlayer.databinding.ActivityFavouriteBinding
import com.example.musicPlayer.databinding.ActivityFeedbackBinding

class FeedbackActivity : AppCompatActivity() {
    lateinit var binding: ActivityFeedbackBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolPinkNav)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        supportActionBar?.title = "Feedback"
        val feedbackMsg = binding.feedbackMsgFA.text.toString() + "\n" + binding.emailFA.text.toString()
        binding.topicFA.text.toString()
        binding.sendFA.setOnClickListener {
        Toast.makeText(this, feedbackMsg, Toast.LENGTH_LONG).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}