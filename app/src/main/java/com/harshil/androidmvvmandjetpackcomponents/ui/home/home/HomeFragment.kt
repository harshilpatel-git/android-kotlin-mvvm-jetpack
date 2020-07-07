package com.harshil.androidmvvmandjetpackcomponents.ui.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.harshil.androidmvvmandjetpackcomponents.R
import com.harshil.androidmvvmandjetpackcomponents.internal.Coroutine
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(), KodeinAware {
    override val kodein by Kodein()
    private val homeViewModelFactory: HomeViewModelFactory by instance()
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        Coroutine.main {
            val quotes = homeViewModel.quotes.await()
            quotes.observe(viewLifecycleOwner, Observer {

            })
        }

        return root
    }

}
