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
import com.example.medmobile.constants.ROLE_PREF
import com.example.medmobile.constants.Role
import com.example.medmobile.constants.TOKEN_PREF
import com.example.medmobile.mvvm.viewModels.SellsViewModel
import com.example.medmobile.toast
import com.example.medmobile.ui.adapters.SellAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sales_to_pharmacy.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SalesToPharmacyFragment : BaseFragment() {
    private val viewModel: SellsViewModel by viewModel()
    private lateinit var sellAdapter: SellAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sales_to_pharmacy, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sellAdapter = SellAdapter()

        requireActivity().toolbar.apply {
            menu.clear()
            val role = prefs.getString(ROLE_PREF, "") ?: ""

            if (role != Role.ACCOUNTANT.name) {
                inflateMenu(R.menu.menu_add)
            }
        }

        setOnClickListeners()
        observeLiveData()

        val token = prefs.getString(TOKEN_PREF, "") ?: ""

        rv_sells.apply {
            adapter = sellAdapter
            layoutManager = LinearLayoutManager(context)
        }

        rv_sells.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.read(token)
                }
            }
        })

        if(sellAdapter.sells.isEmpty()) {
            viewModel.resetHelper()
        }
        viewModel.read(token)
    }

    override fun setOnClickListeners() {
        super.setOnClickListeners()

        requireActivity().toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_add_item -> {
                    findNavController().navigate(R.id.action_SalesToPharmacyFragment_to_createSellFragment)
                    true
                }
                else -> true
            }
        }
    }

    override fun observeLiveData() {
        viewModel.sells.observe(viewLifecycleOwner, Observer {
            sellAdapter.sells += it
            sellAdapter.notifyDataSetChanged()
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            context?.toast(it)
        })
    }
}