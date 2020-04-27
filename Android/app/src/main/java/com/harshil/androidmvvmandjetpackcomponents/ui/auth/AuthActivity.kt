package com.harshil.androidmvvmandjetpackcomponents.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.harshil.androidmvvmandjetpackcomponents.R

class AuthActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        navController = Navigation.findNavController(this, R.id.login_nav_host_fragment)

    }
}
