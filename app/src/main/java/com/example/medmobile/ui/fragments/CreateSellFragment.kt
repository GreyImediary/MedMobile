package com.example.medmobile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.medmobile.R
import com.example.medmobile.checkOnEmptyText
import com.example.medmobile.constants.TOKEN_PREF
import com.example.medmobile.convertDateToIso
import com.example.medmobile.mvvm.model.PostSell
import com.example.medmobile.mvvm.viewModels.SellsViewModel
import com.example.medmobile.toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_create_sell.*
import kotlinx.android.synthetic.main.fragment_create_supply.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CreateSellFragment : BaseFragment() {
    private val viewModel: SellsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_sell, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().toolbar.menu.clear()

        observeLiveData()
        setOnClickListeners()

    }

    override fun observeLiveData() {
        viewModel.createdSell.observe(viewLifecycleOwner, Observer {
            val message = "Продажа ${it.medicine.title} аптеке ${it.pharmacy.title} создана"

            val snackbar = Snackbar.make(
                button_create_sell,
                message,
                Snackbar.LENGTH_SHORT
            )

            snackbar.setAction(R.string.OK) {
                snackbar.dismiss()
            }

            snackbar.show()

            edit_invoice_id.text?.clear()
            edit_medicine_id.text?.clear()
            edit_pharmacy_id.text?.clear()
            edit_shelf_life.text?.clear()
            edit_quantity.text?.clear()
            edit_sell_date.text?.clear()
            edit_production_date.text?.clear()

            hideKeyboard()
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


                        this@CreateSellFragment.edit_sell_date.setText(convertDateToIso(date), TextView.BufferType.EDITABLE)
                    }
                }
            }

            dateFragment.show(requireActivity().supportFragmentManager, "datePicker")
        }

        edit_production_date.setOnClickListener {
            val dateFragment = DatePickerFragment().apply {
                onDateChangeListener = object : OnDateChangeListener {
                    override fun onDateChange(date: Calendar) {

                        this@CreateSellFragment.edit_production_date.setText(convertDateToIso(date))
                    }
                }
            }

            dateFragment.show(requireActivity().supportFragmentManager, "datePicker")
        }

        button_create_sell.setOnClickListener {
            input_invoice_id.checkOnEmptyText("Введите ID накладной")
            input_medicine_id.checkOnEmptyText("Введите ID лекарства")
            input_pharmacy_id.checkOnEmptyText("Введите ID аптеки")
            input_shelf_life.checkOnEmptyText("Введите срок годности")
            input_quantity.checkOnEmptyText("Введите количество")
            input_sell_date.checkOnEmptyText("Выберите дату продажи")
            input_production_date.checkOnEmptyText("Выберите ")

            val invoiceId = edit_invoice_id.text.toString()
            val medicineId = edit_medicine_id.text.toString()
            val pharmacyId = edit_pharmacy_id.text.toString()
            val shelfLife = edit_shelf_life.text.toString()
            val quantity = edit_quantity.text.toString()
            val sellDate = edit_sell_date.text.toString()
            val productionDate = edit_production_date.text.toString()

            if (invoiceId.isNotBlank() && medicineId.isNotBlank() && pharmacyId.isNotBlank() &&
                    shelfLife.isNotBlank() && quantity.isNotBlank() && sellDate.isNotBlank() &&
                    productionDate.isNotBlank()) {

                val postSell = PostSell(
                    quantity.toInt(),
                    sellDate,
                    shelfLife.toInt(),
                    productionDate,
                    invoiceId.toInt(),
                    pharmacyId.toInt(),
                    medicineId.toInt()
                )

                viewModel.create(
                    prefs.getString(TOKEN_PREF, "") ?: "",
                    postSell
                )
            }
        }
    }
}