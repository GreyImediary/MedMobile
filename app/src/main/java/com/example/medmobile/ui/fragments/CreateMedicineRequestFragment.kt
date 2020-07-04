package com.example.medmobile.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.medmobile.R
import com.example.medmobile.checkOnEmptyText
import com.example.medmobile.constants.TOKEN_PREF
import com.example.medmobile.mvvm.model.PostMedicineRequest
import com.example.medmobile.mvvm.viewModels.MedicineRequestViewModel
import com.example.medmobile.toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_create_manufacturer.*
import kotlinx.android.synthetic.main.fragment_create_medicine_request.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateMedicineRequestFragment : BaseFragment() {
    private val viewModel: MedicineRequestViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_medicine_request, null)
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

        viewModel.createdMedicineRequest.observe(viewLifecycleOwner, Observer {
            val message = getString(R.string.request_succeed)

            val snackbar = Snackbar.make(
                create_manuf_button,
                message,
                Snackbar.LENGTH_SHORT
            )

            snackbar.setAction(R.string.OK) {
                snackbar.dismiss()
            }

            snackbar.show()

            edit_request_id.text?.clear()
            edit_request_quantity.text?.clear()

            hideKeyboard()
        })
    }

    override fun setOnClickListeners() {
        button_create_request.setOnClickListener {
            input_request_id.checkOnEmptyText(getString(R.string.medicine_request_id_error))
            input_request_quantity.checkOnEmptyText(getString(R.string.medicine_request_quantity_error))

            val id = edit_request_id.text.toString()
            val quantity = edit_request_quantity.text.toString()

            if (id.isNotBlank() && quantity.isNotBlank()) {
                val postRequest = PostMedicineRequest(
                    quantity.toInt(),
                    id.toInt()
                )

                viewModel.create(
                    prefs.getString(TOKEN_PREF, "") ?: "",
                    postRequest
                )
            }
        }
    }
}