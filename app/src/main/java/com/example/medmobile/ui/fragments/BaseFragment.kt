package com.example.medmobile.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import com.example.medmobile.constants.PREF_NAME

abstract class BaseFragment : Fragment() {
    protected val prefs: SharedPreferences
        get() = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    open fun observeLiveData() {}

    open fun setRoleRestriction() {}

    open fun setOnClickListeners() {}
}