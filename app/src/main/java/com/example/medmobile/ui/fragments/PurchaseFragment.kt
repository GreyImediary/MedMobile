package com.example.medmobile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.R
import com.example.medmobile.constants.TOKEN_PREF
import com.example.medmobile.mvvm.viewModels.MedicineRequestViewModel
import com.example.medmobile.toast
import com.example.medmobile.ui.adapters.MedicineRequestAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_medicine_request.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PurchaseFragment : BaseFragment() {
    private val viewModel: MedicineRequestViewModel by viewModel()
    private lateinit var medicineRequestAdapted: MedicineRequestAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_medicine_request, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        medicineRequestAdapted = MedicineRequestAdapter()

        requireActivity().toolbar.apply {
            menu.clear()
            inflateMenu(R.menu.menu_add)
        }

        observeLiveData()
        setOnClickListeners()

        val token = prefs.getString(TOKEN_PREF, "") ?: ""

        rv_medicine_requests.apply {
            adapter = medicineRequestAdapted
            layoutManager = LinearLayoutManager(context)
        }

        rv_medicine_requests.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.read(token)
                }
            }
        })

        if (medicineRequestAdapted.medicineRequests.isEmpty()) {
            viewModel.resetHelper()
        }

        viewModel.read(token)
    }

    override fun observeLiveData() {
        viewModel.medicineRequests.observe(viewLifecycleOwner, Observer {
            medicineRequestAdapted.medicineRequests += it
            medicineRequestAdapted.notifyDataSetChanged()
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            context?.toast(it)
        })
    }

    override fun setOnClickListeners() {
        requireActivity().toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_add_item -> {
                    findNavController().navigate(R.id.action_PurchaseFragment_to_createMedicineRequestFragment)
                    true
                }
                else -> true
            }
        }
    }
}