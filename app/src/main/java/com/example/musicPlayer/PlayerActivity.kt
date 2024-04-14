package com.example.musicPlayer

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.media.audiofx.AudioEffect
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore.Audio.Media
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicPlayer.databinding.ActivityPlayerBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class PlayerActivity : AppCompatActivity(), ServiceConnection, MediaPlayer.OnCompletionListener {

    companion object{
        lateinit var musicListPA : ArrayList<Music>
        var songPosition: Int = 0
        var isPlaying:Boolean = false
        var musicService: MusicService? = null
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityPlayerBinding
        var repeat: Boolean = false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolPink)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //for starting service
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, this, BIND_AUTO_CREATE)
        startService(intent)
        initializeLayout()

        binding.backBtnPA.setOnClickListener { finish() }

        binding.playPauseBtnPA.setOnClickListener{
            if(isPlaying) pauseMusic()
            else playMusic()
        }
        binding.previousBtnPA.setOnClickListener { prevNextSong(increment = false) }
        binding.nextBtnPA.setOnClickListener { prevNextSong(increment = true) }
        binding.seekBarPA.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) musicService!!.mediaPlayer!!.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })
        binding.repeatBtnPA.setOnClickListener {
            if(!repeat) {
                repeat = true
                binding.repeatBtnPA.setColorFilter(ContextCompat.getColor(this, R.color.purple_500))
            } else {
                repeat = false
                binding.repeatBtnPA.setColorFilter(ContextCompat.getColor(this, R.color.black))

            }
        }

        binding.equalizerBtnPA.setOnClickListener {
            try {
                val eqIntent = Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL)
                eqIntent.putExtra(AudioEffect.EXTRA_AUDIO_SESSION, musicService!!.mediaPlayer!!.audioSessionId)
                eqIntent.putExtra(AudioEffect.EXTRA_PACKAGE_NAME, baseContext.packageName)
                eqIntent.putExtra(AudioEffect.EXTRA_CONTENT_TYPE, AudioEffect.CONTENT_TYPE_MUSIC)
                startActivityForResult(eqIntent, 13)
            } catch (e: Exception) {Toast.makeText(this, "Equalizer Feature not Supported!!", Toast.LENGTH_SHORT).show()}
        }

        binding.timerBtnPA.setOnClickListener {
            showBottomSheetDialog()
        }
    }
    private fun setLayout(){
        Glide.with(this)
            .load(musicListPA[songPosition].artUri)
            .apply(RequestOptions().placeholder(R.drawable.music_player_icon_slash_screen).centerCrop())
            .into(binding.songImgPA)
        binding.songNamePA.text = musicListPA[songPosition].title
        if (repeat) binding.repeatBtnPA.setColorFilter(ContextCompat.getColor(this, R.color.purple_500))
    }

    private fun createMediaPlayer() {
        try {
            if (musicService!!.mediaPlayer == null) musicService!!.mediaPlayer = MediaPlayer()
            musicService!!.mediaPlayer !!.reset()
            musicService!!.mediaPlayer !!.setDataSource(musicListPA[songPosition].path)
            musicService!!.mediaPlayer !!.prepare()
            musicService!!.mediaPlayer !!.start()
            isPlaying = true
            binding.playPauseBtnPA.setIconResource(R.drawable.pause_icon)
            musicService!!.showNotification(R.drawable.pause_icon)
            binding.tvSeekBarStart.text = formatDuration(musicService!!.mediaPlayer!!.currentPosition.toLong())
            binding.tvSeekBarEnd.text = formatDuration(musicService!!.mediaPlayer!!.duration.toLong())
            binding.seekBarPA.progress = 0
            binding.seekBarPA.max = musicService!!.mediaPlayer!!.duration
            musicService!!.mediaPlayer!!.setOnCompletionListener(this)
        } catch (e: Exception) {return}
    }
    private  fun initializeLayout() {
        songPosition = intent.getIntExtra("index", 0)
        when(intent.getStringExtra("class")) {
            "MusicAdapter" ->{
                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.MusicListMA)
                setLayout()

            }
            "MainActivity" ->{
                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.MusicListMA)
                musicListPA.shuffle()
                setLayout()

            }
        }
    }
    private fun playMusic() {
        binding.playPauseBtnPA.setIconResource(R.drawable.pause_icon)
        musicService!!.showNotification(R.drawable.pause_icon)
        isPlaying = true
        musicService!!.mediaPlayer !!.start()
    }

    private fun pauseMusic() {
        binding.playPauseBtnPA.setIconResource(R.drawable.play_icon)
        musicService!!.showNotification(R.drawable.play_icon)
        isPlaying = false
        musicService!!.mediaPlayer !!.pause()
    }

    private fun prevNextSong(increment: Boolean) {
        if (increment)
        {
            setSongPosition(increment = true)
            setLayout()
            createMediaPlayer()
        } else {
            setSongPosition(increment = false)
            setLayout()
            createMediaPlayer()
        }
    }



    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MusicService.MyBinder
        musicService = binder.currentService()
        createMediaPlayer()
        musicService!!.seeBarSetup()

    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }

    override fun onCompletion(mp: MediaPlayer?) {
        setSongPosition(increment = true)
        createMediaPlayer()
        try {
            setLayout()
        } catch (e: Exception) {
            return
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 13 || resultCode == RESULT_OK)
            return
    }

    private fun showBottomSheetDialog() {
        val dialog = BottomSheetDialog(this@PlayerActivity)
        dialog.setContentView(R.layout.bottom_sheet_dialog)
        dialog.show()
        dialog.findViewById<LinearLayout>(R.id.min_15)?.setOnClickListener{
            Toast.makeText(baseContext, "Music will stop after 15 minutes", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.findViewById<LinearLayout>(R.id.min_30)?.setOnClickListener{
            Toast.makeText(baseContext, "Music will stop after 15 minutes", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.findViewById<LinearLayout>(R.id.min_60)?.setOnClickListener{
            Toast.makeText(baseContext, "Music will stop after 15 minutes", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }

}