package com.example.medmobile.ui.fragments

import android.annotation.SuppressLint
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
import com.example.medmobile.mvvm.viewModels.UserViewModel
import com.example.medmobile.ui.adapters.UsersAdapter
import kotlinx.android.synthetic.main.fragment_users.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UsersFragment : BaseFragment() {

    private val userViewModel: UserViewModel by sharedViewModel()
    lateinit var usersAdapter: UsersAdapter

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = prefs.getString(TOKEN_PREF, "") ?: ""

        usersAdapter = UsersAdapter()

        rv_users.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(context)
        }

        rv_users.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    userViewModel.read(token)
                }
            }
        })

        observeLiveData()
        setOnClickListeners()

        if (usersAdapter.users.isEmpty()) {
            userViewModel.resetPageHelper()
        }
        userViewModel.read(token)
    }

    override fun observeLiveData() {
        userViewModel.users.observe(viewLifecycleOwner, Observer {
            usersAdapter.users += it
            usersAdapter.notifyDataSetChanged()
        })
    }

    override fun setOnClickListeners() {
        super.setOnClickListeners()

        user_add_button.setOnClickListener {
            findNavController().navigate(R.id.action_usersFragment_to_createUserFragment)
        }
    }
}