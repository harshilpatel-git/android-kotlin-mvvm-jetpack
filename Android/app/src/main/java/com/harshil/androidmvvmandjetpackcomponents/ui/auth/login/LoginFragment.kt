package com.harshil.androidmvvmandjetpackcomponents.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.harshil.androidmvvmandjetpackcomponents.R
import com.harshil.androidmvvmandjetpackcomponents.data.db.entities.User
import com.harshil.androidmvvmandjetpackcomponents.databinding.LoginFragmentBinding
import com.harshil.androidmvvmandjetpackcomponents.internal.snackbar
import com.harshil.androidmvvmandjetpackcomponents.internal.toast
import com.harshil.androidmvvmandjetpackcomponents.ui.home.HomeActivity
import kotlinx.android.synthetic.main.login_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class LoginFragment : Fragment(), LoginListener, KodeinAware {

    override val kodein by kodein()

    // View model factory class are used to give dependencies to the View model as we
    // can not directly instantiate view model. So we have to create factory and pass
    // it view model provider below
    private val loginViewModelFactory: LoginViewModelFactory by instance()

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

        viewModel =
            ViewModelProviders.of(this, loginViewModelFactory).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loginListener = this

        viewModel.getLoggedInUser().observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                Intent(context, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }

    override fun onLoginStart() {
        context?.applicationContext?.toast("Login started")
    }

    override fun onSuccess(user: User?) {
        // Navigation after success is handled by the live data user stored in the database
    }

    override fun onFailure(reason: String) {
        rootLayout.snackbar(reason)
    }

}
