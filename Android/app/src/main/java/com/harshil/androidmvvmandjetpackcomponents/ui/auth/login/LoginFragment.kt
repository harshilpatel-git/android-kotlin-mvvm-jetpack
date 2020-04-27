package com.harshil.androidmvvmandjetpackcomponents.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.harshil.androidmvvmandjetpackcomponents.R
import com.harshil.androidmvvmandjetpackcomponents.data.db.AppDatabase
import com.harshil.androidmvvmandjetpackcomponents.data.db.entities.User
import com.harshil.androidmvvmandjetpackcomponents.data.network.AuthApi
import com.harshil.androidmvvmandjetpackcomponents.data.repository.AuthRepository
import com.harshil.androidmvvmandjetpackcomponents.databinding.LoginFragmentBinding
import com.harshil.androidmvvmandjetpackcomponents.internal.snackbar
import com.harshil.androidmvvmandjetpackcomponents.internal.toast
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment(), LoginListener {

    companion object {
        fun newInstance() =
            LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: LoginFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.login_fragment, container, false
        )
        val authApi = AuthApi()
        val db = AppDatabase(context?.applicationContext!!)
        val authRepository = AuthRepository(authApi, db)

        // View model factory class are used to give dependencies to the View model as we
        // can not directly instantiate view model. So we have to create factory and pass
        // it view model provider below
        val loginViewModelFactory = LoginViewModelFactory(authRepository)

        viewModel =
            ViewModelProviders.of(this, loginViewModelFactory).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loginListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {

            }
        })
    }

    override fun onLoginStart() {
        context?.applicationContext?.toast("Login started")
    }

    override fun onSuccess(user: User?) {
        rootLayout.snackbar("User: ${user?.name}")
    }

    override fun onFailure(reason: String) {
        rootLayout.snackbar(reason)
    }

}
