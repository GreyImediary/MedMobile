package com.example.medmobile.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.Observer
import com.example.medmobile.R
import com.example.medmobile.checkOnEmptyText
import com.example.medmobile.constants.TOKEN_PREF
import com.example.medmobile.mvvm.model.PostMedicine
import com.example.medmobile.mvvm.viewModels.MedicineViewModel
import com.example.medmobile.toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_create_manufacturer.*
import kotlinx.android.synthetic.main.fragment_create_medicine.*
import kotlinx.android.synthetic.main.fragment_create_user.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateMedicineFragment : BaseFragment() {

    private val viewModel: MedicineViewModel by viewModel()

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_medicine, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().toolbar.menu.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_role,
            resources.getStringArray(R.array.type_array)
        )

        (input_medicine_type.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        observeLiveData()
        setOnClickListeners()
    }

    override fun observeLiveData() {
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            context?.toast(it)
        })

        viewModel.createdMedicine.observe(viewLifecycleOwner, Observer {
            val message = getString(R.string.medicine_created_snackbar_text, it.title)

            val snackbar = Snackbar.make(
                create_manuf_button,
                message,
                Snackbar.LENGTH_SHORT
            )

            snackbar.setAction(R.string.OK) {
                snackbar.dismiss()
            }

            snackbar.show()
        })
    }

    override fun setOnClickListeners() {
        create_medicine_button.setOnClickListener {
            input_medicine_title.checkOnEmptyText(getString(R.string.medicine_title_error))
            input_medicine_price.checkOnEmptyText(getString(R.string.medicine_price_error))
            input_medicine_type.checkOnEmptyText(getString(R.string.medicine_type_hint))
            input_medicine_creator_id.checkOnEmptyText(getString(R.string.medicine_creator_id_error))
            input_medicine_desc.checkOnEmptyText(getString(R.string.medicine_description_error))

            val title = edit_medicine_title.text.toString()
            val price = edit_medicine_price.text.toString()
            val type = edit_medicine_type.text.toString()
            val creatorId = edit_medicine_creator_id.text.toString()
            val desc = edit_medicine_desc.text.toString()

            if (title.isNotBlank() &&
                price.isNotBlank() &&
                type.isNotBlank() &&
                creatorId.isNotBlank() &&
                desc.isNotBlank()) {

                val postMedicine = PostMedicine(
                    title,
                    price.toDouble(),
                    desc,
                    type,
                    creatorId.toInt()
                )
                viewModel.create(
                    prefs.getString(TOKEN_PREF, "") ?: "",
                    postMedicine
                )
            }
        }
    }
}