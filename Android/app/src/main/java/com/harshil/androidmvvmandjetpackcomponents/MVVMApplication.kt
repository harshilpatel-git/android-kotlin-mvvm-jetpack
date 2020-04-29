package com.harshil.androidmvvmandjetpackcomponents

import android.app.Application
import com.harshil.androidmvvmandjetpackcomponents.data.db.AppDatabase
import com.harshil.androidmvvmandjetpackcomponents.data.network.AppApi
import com.harshil.androidmvvmandjetpackcomponents.data.network.NetworkConnectionInterceptor
import com.harshil.androidmvvmandjetpackcomponents.data.repository.AuthRepository
import com.harshil.androidmvvmandjetpackcomponents.data.repository.QuotesRepository
import com.harshil.androidmvvmandjetpackcomponents.ui.auth.login.LoginViewModelFactory
import com.harshil.androidmvvmandjetpackcomponents.ui.auth.signup.SignUpViewModelFactory
import com.harshil.androidmvvmandjetpackcomponents.ui.home.home.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {

        // It provided androidx regarding classes ready to use
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { AppApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { AuthRepository(instance(), instance()) }
        bind() from singleton { QuotesRepository(instance(), instance()) }

        bind() from provider { LoginViewModelFactory(instance()) }
        bind() from provider { SignUpViewModelFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }


    }

}