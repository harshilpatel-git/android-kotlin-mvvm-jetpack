package com.harshil.androidmvvmandjetpackcomponents.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.harshil.androidmvvmandjetpackcomponents.R
import com.harshil.androidmvvmandjetpackcomponents.data.db.entities.User
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
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loginListener = this
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
