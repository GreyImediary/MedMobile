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
import com.example.medmobile.mvvm.viewModels.MedicineViewModel
import com.example.medmobile.toast
import com.example.medmobile.ui.adapters.MedicineAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_medicine.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MedicineFragment : BaseFragment() {
    private val viewModel: MedicineViewModel by viewModel()
    private lateinit var medicineAdapter: MedicineAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_medicine, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        medicineAdapter = MedicineAdapter()

        requireActivity().toolbar.apply {
            menu.clear()
            inflateMenu(R.menu.menu_add)
        }

        observeLiveData()
        setOnClickListeners()

        val token = prefs.getString(TOKEN_PREF, "") ?: ""

        rv_medicine.apply {
            adapter = medicineAdapter
            layoutManager = LinearLayoutManager(context)
        }

        rv_medicine.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.read(token)
                }
            }
        })

        viewModel.resetHelper()
        viewModel.read(token)
    }

    override fun observeLiveData() {
        viewModel.medicines.observe(viewLifecycleOwner, Observer {
            medicineAdapter.medicines += it
            medicineAdapter.notifyDataSetChanged()
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            context?.toast(it)
        })
    }

    override fun setOnClickListeners() {
        requireActivity().toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_add_item -> {
                    findNavController().navigate(R.id.action_medicineFragment_to_createMedicineFragment)
                    true
                }
                else -> true
            }
        }
    }
}