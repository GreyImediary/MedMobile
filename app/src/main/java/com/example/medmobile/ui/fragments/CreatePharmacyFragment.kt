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
import com.example.medmobile.mvvm.model.PostPharmacy
import com.example.medmobile.mvvm.viewModels.MedicineViewModel
import com.example.medmobile.mvvm.viewModels.PharmacyViewModel
import com.example.medmobile.toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_create_manufacturer.*
import kotlinx.android.synthetic.main.fragment_create_pharmacy.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreatePharmacyFragment : BaseFragment() {

    private val viewModel: PharmacyViewModel by viewModel()

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_pharmacy, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().toolbar.menu.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observeLiveData()
        setOnClickListeners()
    }

    override fun observeLiveData() {
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            context?.toast(it)
        })

        viewModel.createdPharmacy.observe(viewLifecycleOwner, Observer {
            val message = getString(R.string.medicine_created_snackbar_text, it.title)

            val snackbar = Snackbar.make(
                create_pharmacy_button,
                message,
                Snackbar.LENGTH_SHORT
            )

            snackbar.setAction(R.string.OK) {
                snackbar.dismiss()
            }

            snackbar.show()

            edit_pharmacy_title.text?.clear()
            edit_pharmacy_address.text?.clear()
            edit_pharmacy_phone.text?.clear()
            edit_pharmacy_supervisor.text?.clear()
        })
    }

    override fun setOnClickListeners() {
        create_pharmacy_button.setOnClickListener {
            input_pharmacy_title.checkOnEmptyText(getString(R.string.pharmacy_name_error))
            input_pharmacy_supervisor.checkOnEmptyText(getString(R.string.pharmacy_supervisor_error))
            input_pharmacy_phone.checkOnEmptyText(getString(R.string.pharmacy_phone_error))
            input_pharmacy_address.checkOnEmptyText(getString(R.string.pharmacy_address_error))

            val title = edit_pharmacy_title.text.toString()
            val supervisor = edit_pharmacy_supervisor.text.toString()
            val phone = edit_pharmacy_phone.text.toString()
            val address = edit_pharmacy_address.text.toString()

            if (title.isNotBlank() &&
                supervisor.isNotBlank() &&
                phone.isNotBlank() &&
                address.isNotBlank()) {

                val postPharmacy = PostPharmacy(title, supervisor, address, phone.toLong())

                viewModel.create(
                    prefs.getString(TOKEN_PREF, "") ?: "",
                    postPharmacy
                )
            }
        }
    }
}