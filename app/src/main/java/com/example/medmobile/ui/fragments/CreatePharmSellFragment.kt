package com.example.medmobile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.medmobile.R
import com.example.medmobile.checkOnEmptyText
import com.example.medmobile.constants.TOKEN_PREF
import com.example.medmobile.convertDateToIso
import com.example.medmobile.mvvm.model.PostPharmSell
import com.example.medmobile.mvvm.viewModels.PharmSellsViewModel
import com.example.medmobile.toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_create_pharm_sell.*
import kotlinx.android.synthetic.main.fragment_create_pharm_sell.button_create_sell
import kotlinx.android.synthetic.main.fragment_create_pharm_sell.edit_sell_date
import kotlinx.android.synthetic.main.fragment_create_pharm_sell.input_medicine_id
import kotlinx.android.synthetic.main.fragment_create_pharm_sell.input_pharmacy_id
import kotlinx.android.synthetic.main.fragment_create_pharm_sell.input_quantity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CreatePharmSellFragment : BaseFragment() {

    private val viewModel: PharmSellsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_pharm_sell, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().toolbar.menu.clear()

        observeLiveData()
        setOnClickListeners()
    }

    override fun observeLiveData() {
        super.observeLiveData()

        viewModel.createdPharmSell.observe(viewLifecycleOwner, Observer {
            val message = "Продажа ${it.medicine.title} аптекой ${it.pharmacy.title} создана"

            val snackbar = Snackbar.make(
                button_create_sell,
                message,
                Snackbar.LENGTH_SHORT
            )

            snackbar.setAction(R.string.OK) {
                snackbar.dismiss()
            }

            snackbar.show()

        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            context?.toast(it)
        })
    }

    override fun setOnClickListeners() {
        super.setOnClickListeners()

        edit_sell_date.setOnClickListener {
            val dateFragment = DatePickerFragment().apply {
                onDateChangeListener = object : OnDateChangeListener {
                    override fun onDateChange(date: Calendar) {


                        this@CreatePharmSellFragment.edit_sell_date.setText(
                            convertDateToIso(date),
                            TextView.BufferType.EDITABLE
                        )
                    }
                }
            }

            dateFragment.show(requireActivity().supportFragmentManager, "datePicker")
        }

        button_create_sell.setOnClickListener {
            input_check_id.checkOnEmptyText("Введите ID чека")
            input_medicine_id.checkOnEmptyText("Введите ID лекарства")
            input_pharmacy_id.checkOnEmptyText("Введите ID аптеки")
            input_quantity.checkOnEmptyText("Введите количество")
            input_sell_date.checkOnEmptyText("Выберите дату продажи")

            val id = edit_check_id.text.toString()
            val medicineId = edit_medicine_id.text.toString()
            val pharmacyId = edit_pharmacy_id.text.toString()
            val quantity = edit_quantity.text.toString()
            val sellDate = edit_sell_date.text.toString()

            if (id.isNotBlank() &&
                medicineId.isNotBlank() &&
                pharmacyId.isNotBlank() &&
                quantity.isNotBlank() && sellDate.isNotBlank()) {

                val postPharmSell = PostPharmSell(
                    quantity.toInt(),
                    sellDate,
                    id.toInt(),
                    pharmacyId.toInt(),
                    medicineId.toInt()
                )

                viewModel.create(
                    prefs.getString(TOKEN_PREF, "") ?: "",
                    postPharmSell
                 )
            }
        }
    }
}