package com.example.medmobile.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.R
import com.example.medmobile.constants.TOKEN_PREF
import com.example.medmobile.mvvm.viewModels.ManufacturerViewModel
import com.example.medmobile.toast
import com.example.medmobile.ui.adapters.ManufacturersAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_manufacturer.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManufacturerFragment : BaseFragment() {
    private val viewModel: ManufacturerViewModel by viewModel()
    private lateinit var manufacturersAdapter: ManufacturersAdapter

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_manufacturer, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().toolbar.apply {
            menu.clear()
            inflateMenu(R.menu.menu_add)
        }

        val token = prefs.getString(TOKEN_PREF, "") ?: ""

        manufacturersAdapter = ManufacturersAdapter()

        rv_manufacturers.apply {
            adapter = manufacturersAdapter
            layoutManager = LinearLayoutManager(context)
        }

        observeLiveData()
        setOnClickListeners()

        rv_manufacturers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.read(token)
                }
            }
        })

        if (manufacturersAdapter.manufacturers.isEmpty()) {
            viewModel.resetHelper()
        }

        viewModel.read(token)
    }

    override fun observeLiveData() {
        super.observeLiveData()

        viewModel.manufacturers.observe(viewLifecycleOwner, Observer {
            manufacturersAdapter.manufacturers += it
            manufacturersAdapter.notifyDataSetChanged()
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
                    findNavController().navigate(R.id.action_manufacturerFragment_to_createManufacturerFragment)
                    true
                }
                else -> true
            }
        }
    }

}