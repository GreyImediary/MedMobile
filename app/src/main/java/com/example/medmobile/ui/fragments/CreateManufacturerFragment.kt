package com.example.medmobile.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.medmobile.R
import com.example.medmobile.checkOnEmptyText
import com.example.medmobile.constants.TOKEN_PREF
import com.example.medmobile.mvvm.model.PostManufacturer
import com.example.medmobile.mvvm.viewModels.ManufacturerViewModel
import com.example.medmobile.toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_create_manufacturer.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateManufacturerFragment : BaseFragment() {

    private val viewModel: ManufacturerViewModel by viewModel()

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_manufacturer, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().toolbar.menu.clear()

        setOnClickListeners()
        observeLiveData()
    }

    override fun observeLiveData() {
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            context?.toast(it)
        })

        viewModel.createdManufacturer.observe(viewLifecycleOwner, Observer {
            val message = getString(R.string.manufacturer_created_snackbar_text, it.title)

            val snackbar = Snackbar.make(
                create_manuf_button,
                message,
                Snackbar.LENGTH_SHORT
            )

            snackbar.setAction(R.string.OK) {
                snackbar.dismiss()
            }

            snackbar.show()

            edit_manuf_name.text?.clear()
            edit_manuf_inn.text?.clear()
            edit_manuf_phone.text?.clear()
            edit_manuf_address.text?.clear()

            hideKeyboard()
        })
    }

    override fun setOnClickListeners() {
        create_manuf_button.setOnClickListener {
            input_manuf_title.checkOnEmptyText(getString(R.string.manufacturer_name_error))
            input_manuf_inn.checkOnEmptyText(getString(R.string.manufacturer_inn_error))
            input_manuf_address.checkOnEmptyText(getString(R.string.manufacturer_address_error))
            input_manuf_phone.checkOnEmptyText(getString(R.string.manufacturer_phone_error))

            val name = edit_manuf_name.text.toString()
            val inn = edit_manuf_inn.text.toString()
            val phone = edit_manuf_phone.text.toString()
            val address = edit_manuf_address.text.toString()

            if (name.isNotBlank() && inn.isNotBlank() && phone.isNotBlank() && address.isNotBlank()) {
                val postManufacturer = PostManufacturer(
                    name,
                    inn.toLong(),
                    address,
                    phone.toLong()
                )

                viewModel.create(
                    prefs.getString(TOKEN_PREF, "") ?: "",
                    postManufacturer
                )
            }
        }
    }
}