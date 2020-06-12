package com.example.medmobile.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.Observer
import com.example.medmobile.R
import com.example.medmobile.checkOnEmptyText
import com.example.medmobile.constants.TOKEN_PREF
import com.example.medmobile.constants.LOGIN_PREF
import com.example.medmobile.constants.PASS_PREF
import com.example.medmobile.constants.USER_ID_EXTRA
import com.example.medmobile.mvvm.viewModels.LogInViewModel
import com.example.medmobile.ui.activities.baseActivity.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {
    private val logInViewModel: LogInViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        observeLiveData()
        checkLogin()
        setOnClickListeners()
    }
    
    override fun setOnClickListeners() {
        button_login.setOnClickListener {
            val login = editTextLogin.text.toString()
            val password = editTextPassword.text.toString()

            inputTextLogin.checkOnEmptyText(getString(R.string.empty_login_error))
            inputTextPassword.checkOnEmptyText(getString(R.string.empty_password_error))

            if (login.isNotBlank() && password.isNotBlank()) {
                logInViewModel.login(login, password)
            }
        }
    }

    private fun checkLogin() {
        val login = prefs.getString(LOGIN_PREF, "") ?: ""
        val password = prefs.getString(PASS_PREF, "") ?: ""

        if (login.isNotBlank() && password.isNotBlank()) {
            logInViewModel.login(login, password)
        }
    }

    override fun observeLiveData() {
        logInViewModel.tokenLiveData.observe(this, Observer {
            prefs.edit {
                putString(TOKEN_PREF, it)

                if (!editTextLogin.text.isNullOrBlank() && !editTextPassword.text.isNullOrBlank()) {
                    putString(LOGIN_PREF, editTextLogin.text.toString())
                    putString(PASS_PREF, editTextPassword.text.toString())
                }
            }


            val userId = logInViewModel.decodeUserIdFromToken(it)
            startActivity(Intent(this, MainActivity::class.java).apply {
                putExtra(USER_ID_EXTRA, userId)
            })
            finish()
        })


        logInViewModel.tokenError.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

}