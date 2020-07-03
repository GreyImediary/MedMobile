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
import com.example.medmobile.mvvm.viewModels.PharmSellsViewModel
import com.example.medmobile.toast
import com.example.medmobile.ui.adapters.PharmSellAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_pharm_sells.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SalesByPharmacyFragment : BaseFragment() {
    private val viewModel: PharmSellsViewModel by viewModel()
    private lateinit var pharmSellAdapter: PharmSellAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pharm_sells, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pharmSellAdapter = PharmSellAdapter()

        requireActivity().toolbar.apply {
            menu.clear()
            inflateMenu(R.menu.menu_add)
        }

        setOnClickListeners()
        observeLiveData()

        val token = prefs.getString(TOKEN_PREF, "") ?: ""

        rv_pharm_sells.apply {
            adapter = pharmSellAdapter
            layoutManager = LinearLayoutManager(context)
        }

        rv_pharm_sells.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.read(token)
                }
            }
        })

        if(pharmSellAdapter.pharmSells.isEmpty()) {
            viewModel.resetHelper()
        }
        viewModel.read(token)
    }

    override fun observeLiveData() {
        viewModel.pharmSells.observe(viewLifecycleOwner, Observer {
            pharmSellAdapter.pharmSells += it
            pharmSellAdapter.notifyDataSetChanged()
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            context?.toast(it)
        })
    }

    override fun setOnClickListeners() {
        super.setOnClickListeners()
        requireActivity().toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_add_item -> {
                    findNavController().navigate(R.id.action_SalesByPharmacyFragment_to_createPharmSellFragment)
                    true
                }
                else -> true
            }
        }
    }
}