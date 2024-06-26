package com.example.myapplication14

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication14.databinding.ActivityMainBinding
import com.example.myapplication14.ui.main.MainFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}