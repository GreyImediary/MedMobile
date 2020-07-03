package com.example.medmobile.ui.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

interface OnDateChangeListener {
    fun onDateChange(date: Calendar)
}

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    lateinit var onDateChangeListener: OnDateChangeListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = Calendar.getInstance().apply {
            set(year, month, dayOfMonth)
        }

        onDateChangeListener.onDateChange(date)
    }
}