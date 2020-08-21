package com.harshil.androidmvvmandjetpackcomponents.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.harshil.androidmvvmandjetpackcomponents.R
import com.harshil.androidmvvmandjetpackcomponents.data.db.AppDatabase
import com.harshil.androidmvvmandjetpackcomponents.data.network.AppApi
import com.harshil.androidmvvmandjetpackcomponents.data.network.NetworkConnectionInterceptor
import com.harshil.androidmvvmandjetpackcomponents.data.repository.AuthRepository
import com.harshil.androidmvvmandjetpackcomponents.databinding.LoginFragmentBinding
import com.harshil.androidmvvmandjetpackcomponents.internal.APIException
import com.harshil.androidmvvmandjetpackcomponents.internal.NoConnectivityException
import com.harshil.androidmvvmandjetpackcomponents.internal.snackbar
import com.harshil.androidmvvmandjetpackcomponents.ui.home.HomeActivity
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.login_fragment, container, false
        )
        viewModel =
            ViewModelProvider(
                // View model factory class are used to give dependencies to the View model as we
                // can not directly instantiate view model. So we have to create factory and pass
                // it view model provider below
                this, LoginViewModelFactory(
                    AuthRepository(
                        AppApi.invoke(NetworkConnectionInterceptor(requireContext().applicationContext)),
                        AppDatabase.invoke(requireContext().applicationContext)
                    )
                )
            ).get(LoginViewModel::class.java)

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getLoggedInUser().observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                Intent(context, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

        binding.loginButton.setOnClickListener {
            loginUserClicked()
        }

        binding.signUpButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }
    }

    private fun loginUserClicked() {
        val username = binding.usernameEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        // TODO: validate user inputs here

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val loginResponse = viewModel.userLogin(username, password)
                loginResponse.user?.let {
                    viewModel.saveUser(loginResponse.user)
                    return@launch
                }
                rootLayout.snackbar(loginResponse.message)
            } catch (e: APIException) {
                rootLayout.snackbar(e.message.toString())
            } catch (e: NoConnectivityException) {
                rootLayout.snackbar(e.message.toString())
            }
        }
    }
}
