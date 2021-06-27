package com.example.mytasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mytasks.forms.MainScreenFragment
import com.example.mytasks.models.Priority
import com.example.mytasks.models.TaskEntity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentMainScreen = MainScreenFragment.newInstance("", "")

        supportFragmentManager.beginTransaction()
            .add(R.id.flFragmentContainer, fragmentMainScreen, null)
            .addToBackStack("backstack")
            .commit()
    }



}