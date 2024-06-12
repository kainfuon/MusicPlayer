package com.example.musicPlayer

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.musicPlayer.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MainActivity.currentThemeNav[MainActivity.themeIndex])
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        when(MainActivity.themeIndex){
            0 -> binding.coolPinkTheme.setBackgroundColor(Color.GRAY)
            1 -> binding.coolBlueTheme.setBackgroundColor(Color.YELLOW)
            2 -> binding.coolPurpleTheme.setBackgroundColor(Color.YELLOW)
            3 -> binding.coolGreenTheme.setBackgroundColor(Color.YELLOW)
            4 -> binding.coolBlackTheme.setBackgroundColor(Color.GRAY)
        }
        binding.coolPinkTheme.setOnClickListener { saveTheme(0) }
        binding.coolBlueTheme.setOnClickListener { saveTheme(1) }
        binding.coolPurpleTheme.setOnClickListener { saveTheme(2) }
        binding.coolGreenTheme.setOnClickListener { saveTheme(3) }
        binding.coolBlackTheme.setOnClickListener { saveTheme(4) }
        binding.settingImage.setImageResource(R.drawable.settings_icon)
        binding.versionName.text = setVersionDetails()
//        binding.sortBtn.setOnClickListener {
//            val menuList = arrayOf("Recently Added", "Song Title", "File Size")
//            var currentSort = MainActivity.sortOrder
//            val builder = MaterialAlertDialogBuilder(this)
//            builder.setTitle("Sorting")
//                .setPositiveButton("OK"){ _, _ ->
//                    val editor = getSharedPreferences("SORTING", MODE_PRIVATE).edit()
//                    editor.putInt("sortOrder", currentSort)
//                    editor.apply()
//                }
//                .setSingleChoiceItems(menuList, currentSort){ _,which->
//                    currentSort = which
//                }
//            val customDialog = builder.create()
//            customDialog.show()
//
//            setDialogBtnBackground(this, customDialog)
//        }
    }

    private fun saveTheme(index: Int){
        if(MainActivity.themeIndex != index){
            val editor = getSharedPreferences("THEMES", MODE_PRIVATE).edit()
            editor.putInt("themeIndex", index)
            editor.apply()
            val builder = MaterialAlertDialogBuilder(this)
            builder.setTitle("Apply Theme")
                .setMessage("Do you want to apply theme?")
                .setPositiveButton("Yes"){ _, _ ->
                    exitApplication()
                }
                .setNegativeButton("No"){dialog, _ ->
                    dialog.dismiss()
                }
            val customDialog = builder.create()
            customDialog.show()

            setDialogBtnBackground(this, customDialog)
        }
    }
    private fun setVersionDetails():String{
        return "Version Name: ${BuildConfig.VERSION_NAME}"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}