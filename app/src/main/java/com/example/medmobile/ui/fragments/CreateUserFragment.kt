package com.example.medmobile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.Observer
import com.example.medmobile.R
import com.example.medmobile.checkOnEmptyText
import com.example.medmobile.constants.TOKEN_PREF
import com.example.medmobile.mvvm.model.PostUser
import com.example.medmobile.mvvm.viewModels.UserViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_create_user.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateUserFragment : BaseFragment() {

    private val userViewModel: UserViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_user, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_role,
            resources.getStringArray(R.array.role_array)
        )
        (input_user_role.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        observeLiveData()
        setOnClickListeners()
    }

    override fun observeLiveData() {
        userViewModel.createdUser.observe(viewLifecycleOwner, Observer {
            val message = getString(R.string.user_created_snackbar_text, it.name)

            val snackbar = Snackbar.make(
                create_user_button,
                message,
                Snackbar.LENGTH_SHORT
            )
            snackbar.setAction(R.string.OK) {
                snackbar.dismiss()
            }

            snackbar.show()
        })
    }

    override fun setOnClickListeners() {
        create_user_button.setOnClickListener {
            input_user_login.checkOnEmptyText(getString(R.string.user_login_error))
            input_user_password.checkOnEmptyText(getString(R.string.user_password_error))
            input_user_name.checkOnEmptyText(getString(R.string.user_name_error))
            input_user_role.checkOnEmptyText(getString(R.string.user_role_error))
            input_user_phone.checkOnEmptyText(getString(R.string.user_phone_error))

            val login = edit_user_login.text.toString()
            val password = edit_user_password.text.toString()
            val name = edit_user_name.text.toString()
            val phone = edit_user_phone.text.toString()
            val role = edit_user_role.text.toString()

            if (login.length < 3) {
                input_user_login.error = getString(R.string.user_login_error)
            } else {
                input_user_login.error = null
            }

            if (password.length !in 8..50) {
                input_user_password.error = getString(R.string.user_password_error)
            } else {
                input_user_password.error = null
            }

            if (name.length < 3) {
                input_user_name.error = getString(R.string.user_name_error)
            } else {
                input_user_name.error = null
            }


            if (login.isNotBlank() &&
                password.isNotBlank() &&
                name.isNotBlank() &&
                phone.isNotBlank() &&
                role.isNotBlank()
            ) {

                userViewModel.create(
                    prefs.getString(TOKEN_PREF, "") ?: "",
                    PostUser(login, password, name, phone.toLong(), role)
                )
            }
        }
    }
}