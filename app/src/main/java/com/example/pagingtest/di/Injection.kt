package com.example.pagingtest.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.pagingtest.data.CheeseDataSource
import com.example.pagingtest.data.CheeseDb
import com.example.pagingtest.repository.CheeseRepository
import com.example.pagingtest.vm.CheeseViewModel
import com.example.pagingtest.vm.CheeseViewModelFactory

object Injection {

    fun provideCheeseViewModel(appCompatActivity: AppCompatActivity) =
        ViewModelProviders.of(
            appCompatActivity,
            provideCheeseViewModelFactory(appCompatActivity)
        ).get(CheeseViewModel::class.java)

    private fun provideCheeseViewModelFactory(context: Context): ViewModelProvider.Factory =
        CheeseViewModelFactory(provideCheeseRepository(context))

    private fun provideCheeseRepository(context: Context) =
        CheeseRepository(
            CheeseDataSource(
                CheeseDb.get(
                    context
                ).cheeseDao()
            )
        )
}
