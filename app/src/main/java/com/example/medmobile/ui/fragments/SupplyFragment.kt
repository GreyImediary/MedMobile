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
import com.example.medmobile.mvvm.viewModels.SupplyViewModel
import com.example.medmobile.toast
import com.example.medmobile.ui.adapters.SupplyAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_supplies.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SupplyFragment : BaseFragment() {
    private val viewModel: SupplyViewModel by viewModel()
    private lateinit var supplyAdapter: SupplyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_supplies, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        supplyAdapter = SupplyAdapter()

        requireActivity().toolbar.apply {
            menu.clear()
            inflateMenu(R.menu.menu_add)
        }

        observeLiveData()
        setOnClickListeners()

        val token = prefs.getString(TOKEN_PREF, "") ?: ""

        rv_supplies.apply {
            adapter = supplyAdapter
            layoutManager = LinearLayoutManager(context)
        }

        rv_supplies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.read(token)
                }
            }
        })

        if(supplyAdapter.supplies.isEmpty()) {
            viewModel.resetHelper()
        }
        viewModel.read(token)
    }

    override fun observeLiveData() {
        viewModel.supplies.observe(viewLifecycleOwner, Observer {
            supplyAdapter.supplies += it
            supplyAdapter.notifyDataSetChanged()
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            context?.toast(it)
        })
    }

    override fun setOnClickListeners() {
        requireActivity().toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_add_item -> {
                    findNavController().navigate(R.id.action_SupplyFragment_to_createSupplyFragment)
                    true
                }
                else -> true
            }
        }
    }
}