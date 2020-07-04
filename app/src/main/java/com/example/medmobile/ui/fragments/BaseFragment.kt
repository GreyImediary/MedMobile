package com.example.medmobile.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.medmobile.constants.PREF_NAME

abstract class BaseFragment : Fragment() {
    protected val prefs: SharedPreferences
        get() = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    open fun observeLiveData() {}

    open fun setRoleRestriction() {}

    open fun setOnClickListeners() {}

    protected fun hideKeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        var view = requireActivity().currentFocus

        if (view == null) {
            view = View(activity)
        }

        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}