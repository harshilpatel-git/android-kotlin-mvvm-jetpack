package com.harshil.androidmvvmandjetpackcomponents

import android.app.Application
import com.harshil.androidmvvmandjetpackcomponents.data.db.AppDatabase
import com.harshil.androidmvvmandjetpackcomponents.data.network.AuthApi
import com.harshil.androidmvvmandjetpackcomponents.data.network.NetworkConnectionInterceptor
import com.harshil.androidmvvmandjetpackcomponents.data.repository.AuthRepository
import com.harshil.androidmvvmandjetpackcomponents.ui.auth.login.LoginViewModelFactory
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
        bind() from singleton { AuthApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { AuthRepository(instance(), instance()) }

        bind() from provider { LoginViewModelFactory(instance()) }

    }

}