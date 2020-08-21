package com.harshil.androidmvvmandjetpackcomponents.ui.auth.signup

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
import com.harshil.androidmvvmandjetpackcomponents.databinding.SignUpFragmentBinding
import com.harshil.androidmvvmandjetpackcomponents.internal.APIException
import com.harshil.androidmvvmandjetpackcomponents.internal.NoConnectivityException
import com.harshil.androidmvvmandjetpackcomponents.internal.snackbar
import com.harshil.androidmvvmandjetpackcomponents.ui.home.HomeActivity
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: SignUpFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.sign_up_fragment, container, false
        )

        viewModel =
            ViewModelProvider(
                this, SignUpViewModelFactory(
                    AuthRepository(
                        AppApi.invoke(NetworkConnectionInterceptor(requireContext().applicationContext)),
                        AppDatabase.invoke(requireContext().applicationContext)
                    )
                )
            ).get(SignUpViewModel::class.java)
        binding.lifecycleOwner = this

        return inflater.inflate(R.layout.sign_up_fragment, container, false)
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

        binding.SignUpButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val dob = binding.dobEditText.text.toString()
            val email = binding.emialEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    val signUpResponse = viewModel.signUpUser(name, dob, email, password)
                    signUpResponse.user?.let {
                        viewModel.saveUser(signUpResponse.user)
                        return@launch
                    }
                    rootLayout.snackbar(signUpResponse.message)
                } catch (e: APIException) {
                    rootLayout.snackbar(e.message.toString())
                } catch (e: NoConnectivityException) {
                    rootLayout.snackbar(e.message.toString())
                }
            }
        }

        binding.backToLoginButton.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }
    }
}
