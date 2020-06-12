package com.example.medmobile.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.medmobile.R
import com.example.medmobile.constants.ROLE_PREF
import com.example.medmobile.constants.Role
import com.example.medmobile.mvvm.viewModels.UserViewModel
import com.example.medmobile.visible
import kotlinx.android.synthetic.main.fragment_directories.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DirectoriesFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_directories, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        users_button.setOnClickListener {
            findNavController().navigate(R.id.action_DirectoriesFragment_to_usersFragment)
        }

        setRoleRestriction()
    }

    override fun setRoleRestriction() {
        when(prefs.getString(ROLE_PREF, "")) {
            Role.DIRECTOR.name -> {
                users_button.visible()
            }
        }
    }
}