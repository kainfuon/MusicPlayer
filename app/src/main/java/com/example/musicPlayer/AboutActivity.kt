package com.example.musicPlayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.musicPlayer.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MainActivity.currentThemeNav[MainActivity.themeIndex])
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "About"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.aboutText.text = aboutText()
        binding.aboutImage.setImageResource(R.drawable.music_icon)


        // Thêm xử lý sự kiện nút back

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun aboutText(): String {
        return "Welcome to our music player app!\n\n" +
                "This app is designed to provide you with a wonderful music listening experience.\n\n" +
                "Some key features of the app include:\n" +
                "• Create and manage playlists\n" +
                "• Add songs to your favorites list\n" +
                "• Customize the app theme to your liking\n" +
                "• Share your favorite songs with friends\n" +
                "• Set a sleep timer to stop playback\n" +
                "• Support for playback on both mobile\n" +
                "• Intuitive and user-friendly interface\n\n" +
                "If you have any feedback or suggestions, we would love to hear from you.\n\n" +
                "Enjoy your music!"
    }
}