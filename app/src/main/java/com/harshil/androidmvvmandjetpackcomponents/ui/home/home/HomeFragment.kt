package com.harshil.androidmvvmandjetpackcomponents.ui.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.harshil.androidmvvmandjetpackcomponents.R
import com.harshil.androidmvvmandjetpackcomponents.data.db.AppDatabase
import com.harshil.androidmvvmandjetpackcomponents.data.network.AppApi
import com.harshil.androidmvvmandjetpackcomponents.data.network.NetworkConnectionInterceptor
import com.harshil.androidmvvmandjetpackcomponents.data.preferences.PreferenceProvider
import com.harshil.androidmvvmandjetpackcomponents.data.repository.QuotesRepository
import com.harshil.androidmvvmandjetpackcomponents.internal.Coroutine

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(
                this, HomeViewModelFactory(
                    QuotesRepository(
                        AppApi.invoke(NetworkConnectionInterceptor(requireContext().applicationContext)),
                        AppDatabase.invoke(requireContext().applicationContext),
                        PreferenceProvider(requireContext().applicationContext)
                    )
                )
            ).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        Coroutine.main {
            val quotes = homeViewModel.quotes.await()
            quotes.observe(viewLifecycleOwner, Observer {

            })
        }

        return root
    }

}
