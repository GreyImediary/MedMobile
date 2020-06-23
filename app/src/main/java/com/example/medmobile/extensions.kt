package com.example.medmobile

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IdRes
import com.google.android.material.textfield.TextInputLayout

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, length).show()

fun TextInputLayout.checkOnEmptyText(errorMessage: String) {
    error = if (editText?.text.isNullOrBlank()) {
        errorMessage
    } else {
        null
    }
}

fun ViewGroup.inflate(layout: Int) =
    LayoutInflater.from(context).inflate(layout, this, false)

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun MenuItem.visible() {
    isVisible = true
}

fun MenuItem.invisible() {
    isVisible = false
}