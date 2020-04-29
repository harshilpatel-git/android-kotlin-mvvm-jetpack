package com.harshil.androidmvvmandjetpackcomponents.ui.auth.signup

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
import com.harshil.androidmvvmandjetpackcomponents.data.network.response.SignUpResponse
import com.harshil.androidmvvmandjetpackcomponents.databinding.SignUpFragmentBinding
import com.harshil.androidmvvmandjetpackcomponents.internal.snackbar
import com.harshil.androidmvvmandjetpackcomponents.ui.auth.login.LoginViewModelFactory
import com.harshil.androidmvvmandjetpackcomponents.ui.home.HomeActivity
import kotlinx.android.synthetic.main.login_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SignUpFragment : Fragment(), SignUpListener, KodeinAware {

    override val kodein by closestKodein()

    // View model factory class are used to give dependencies to the View model as we
    // can not directly instantiate view model. So we have to create factory and pass
    // it view model provider below

    companion object {
        fun newInstance() =
            SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel
    private val signUpViewModelFactory: LoginViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: SignUpFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.sign_up_fragment, container, false
        )

        viewModel =
            ViewModelProviders.of(this, signUpViewModelFactory).get(SignUpViewModel::class.java)
        binding.viewModel = viewModel
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
    }

    override fun onSuccess(signUpResponse: SignUpResponse) {
        // Navigation after success is handled by the live data user stored in the database
    }

    override fun onFailure(reason: String) {
        rootLayout.snackbar(reason)
    }
}
