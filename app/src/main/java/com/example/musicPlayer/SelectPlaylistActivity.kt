package com.example.musicPlayer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicPlayer.databinding.ActivitySelectPlaylistBinding
import com.example.musicPlayer.databinding.ActivitySelectionBinding

class SelectPlaylistActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectPlaylistBinding
    private lateinit var adapter: PlaylistViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectPlaylistBinding.inflate(layoutInflater)
        setTheme(MainActivity.currentTheme[MainActivity.themeIndex])
        setContentView(binding.root)
        binding.playlistSelectionRV.setItemViewCacheSize(13)
        binding.playlistSelectionRV.setHasFixedSize(true)
        binding.playlistSelectionRV.layoutManager = GridLayoutManager(this, 2)
        adapter = PlaylistViewAdapter(this, playlistList = PlaylistActivity.musicPlaylist.ref, SelectPlaylistActivity = true)
        binding.playlistSelectionRV.adapter = adapter
        binding.backBtnPS.setOnClickListener { finish() }

        //for search View
//        binding.searchViewPS.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean = true
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                MainActivity.playlistListSearch = ArrayList()
//                if (newText != null) {
//                    val userInput = newText.lowercase()
//                    for (playlist in MainActivity.PlaylistListMA)
//                        if (playlist.name.lowercase().contains(userInput))
//                            MainActivity.playlistListSearch.add(playlist)
//                    MainActivity.search = true
//                    adapter.updatePlaylistList(searchList = MainActivity.playlistListSearch)
//                }
//                return true
//            }
//        })
    }

}