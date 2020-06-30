package com.example.medmobile.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.core.content.edit
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.medmobile.R
import com.example.medmobile.constants.ROLE_PREF
import com.example.medmobile.constants.Role
import com.example.medmobile.constants.TOKEN_PREF
import com.example.medmobile.constants.USER_ID_EXTRA
import com.example.medmobile.invisible
import com.example.medmobile.mvvm.viewModels.UserViewModel
import com.example.medmobile.toast
import com.example.medmobile.ui.activities.baseActivity.BaseActivity
import com.example.medmobile.ui.fragments.DirectoriesFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    private val viewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeLiveData()

        val userId = intent.getIntExtra(USER_ID_EXTRA, 0)

        viewModel.getCurrentUser(prefs.getString(TOKEN_PREF, "")!!, userId)

        val navController = findNavController(R.id.nav_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)
        nav_view.setupWithNavController(navController)
        toolbar.setupWithNavController(navController, appBarConfiguration)

    }

    override fun observeLiveData() {
        viewModel.currentUser.observe(this, Observer {
            prefs.edit {
                putString(ROLE_PREF, it.role)
            }

            setRoleRestriction(it.role)
        })

        viewModel.errorMessage.observe(this, Observer {
            toast(it)
        })
    }

    override fun setOnClickListeners() {}

    private fun setRoleRestriction(role: String) {
        val menu = nav_view.menu

        when (role) {
            Role.OPERATOR.name -> {
                menu.getItem(3).invisible()
            }

            Role.SUPPLIER.name -> {
                menu.getItem(1).invisible()
                menu.getItem(3).invisible()
                menu.getItem(4).invisible()
            }
        }

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_fragment)
        val fragment = navHost?.childFragmentManager?.fragments?.get(0)

        if (fragment != null && fragment is DirectoriesFragment) {
            fragment.setRoleRestriction()
        }
    }

}