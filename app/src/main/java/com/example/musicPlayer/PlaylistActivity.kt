package com.example.musicPlayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.musicPlayer.databinding.ActivityPlayerBinding
import com.example.musicPlayer.databinding.ActivityPlaylistBinding

class PlaylistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolPink)
//        setContentView(R.layout.activity_playlist)
        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}