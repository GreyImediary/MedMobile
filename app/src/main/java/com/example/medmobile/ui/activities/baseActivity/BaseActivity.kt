package com.example.medmobile.ui.activities.baseActivity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.medmobile.constants.PREF_NAME

abstract class BaseActivity : AppCompatActivity() {
    protected val prefs: SharedPreferences
        get() = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    abstract fun setOnClickListeners()

    abstract fun observeLiveData()
}