package com.example.medmobile.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import com.example.medmobile.R
import com.example.medmobile.constants.ROLE_PREF
import com.example.medmobile.constants.Role
import com.example.medmobile.ui.activities.LoginActivity
import com.example.medmobile.visible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_directories.*

class DirectoriesFragment : BaseFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_directories, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().toolbar.apply {
            menu.clear()
            inflateMenu(R.menu.menu_exit)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()

    }

    override fun onStart() {
        super.onStart()
        setRoleRestriction()

        requireActivity().toolbar.apply {
            menu.clear()
            inflateMenu(R.menu.menu_exit)
        }
    }

    override fun setRoleRestriction() {
        when(prefs.getString(ROLE_PREF, "")) {
            Role.DIRECTOR.name -> {
                users_button.visible()
            }
        }
    }

    override fun setOnClickListeners() {
        requireActivity().toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_exit_item -> {
                    prefs.edit {
                        clear()
                    }
                    startActivity(Intent(context, LoginActivity::class.java))
                    requireActivity().finish()
                    true
                }
                else -> {
                    true
                }
            }
        }

        users_button.setOnClickListener {
            findNavController().navigate(R.id.action_DirectoriesFragment_to_usersFragment)
        }

        remedy_producers_button.setOnClickListener {
            findNavController().navigate(R.id.action_DirectoriesFragment_to_manufacturerFragment)
        }

        remedies_button.setOnClickListener {
            findNavController().navigate(R.id.action_DirectoriesFragment_to_medicineFragment)
        }

        pharmacies_button.setOnClickListener {
            findNavController().navigate(R.id.action_DirectoriesFragment_to_pharmacyFragment)
        }
    }
}