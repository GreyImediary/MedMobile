package com.example.medmobile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.medmobile.R
import com.example.medmobile.checkOnEmptyText
import com.example.medmobile.constants.TOKEN_PREF
import com.example.medmobile.mvvm.model.PostSupply
import com.example.medmobile.mvvm.viewModels.SupplyViewModel
import com.example.medmobile.toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_create_pharmacy.*
import kotlinx.android.synthetic.main.fragment_create_supply.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateSupplyFragment : BaseFragment() {
    private val viewModel: SupplyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_supply, null)
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

        viewModel.createdSupply.observe(viewLifecycleOwner, Observer {
            val message = getString(R.string.supply_succeed)

            val snackbar = Snackbar.make(
                button_create_supply,
                message,
                Snackbar.LENGTH_SHORT
            )

            snackbar.setAction(R.string.OK) {
                snackbar.dismiss()
            }

            snackbar.show()

            edit_supply_positions?.text?.clear()

            hideKeyboard()
        })
    }

    override fun setOnClickListeners() {
        button_create_supply.setOnClickListener {
            input_supply_positions.checkOnEmptyText(getString(R.string.supply_positions_error))

            val positions = edit_supply_positions.text.toString()

            if (positions.isNotBlank()) {
                viewModel.create(
                    prefs.getString(TOKEN_PREF, "") ?: "",
                    PostSupply(positions)
                )
            }
        }
    }
}