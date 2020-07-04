package com.example.medmobile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.R
import com.example.medmobile.constants.TOKEN_PREF
import com.example.medmobile.mvvm.viewModels.PharmacyViewModel
import com.example.medmobile.toast
import com.example.medmobile.ui.adapters.PharmacyAdapter
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_pharmacy.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PharmacyFragment : BaseFragment() {
    private val viewModel: PharmacyViewModel by viewModel()
    private lateinit var pharmacyAdapter: PharmacyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pharmacy, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pharmacyAdapter = PharmacyAdapter()

        requireActivity().toolbar.apply {
            menu.clear()
            inflateMenu(R.menu.menu_add)
        }

        observeLiveData()
        setOnClickListeners()

        val token = prefs.getString(TOKEN_PREF, "") ?: ""

        rv_pharmacies.apply {
            adapter = pharmacyAdapter
            layoutManager = LinearLayoutManager(context)
        }

        rv_pharmacies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.read(token)
                }
            }
        })

        if(pharmacyAdapter.pharmacies.isEmpty()) {
            viewModel.resetHelper()
        }
        viewModel.read(token)
    }

    override fun observeLiveData() {
        viewModel.pharmacies.observe(viewLifecycleOwner, Observer {
            pharmacyAdapter.pharmacies += it
            pharmacyAdapter.notifyDataSetChanged()
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            context?.toast(it)
        })
    }

    override fun setOnClickListeners() {
        requireActivity().toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_add_item -> {
                    findNavController().navigate(R.id.action_pharmacyFragment_to_createPharmacyFragment)
                    true
                }
                else -> true
            }
        }
    }
}