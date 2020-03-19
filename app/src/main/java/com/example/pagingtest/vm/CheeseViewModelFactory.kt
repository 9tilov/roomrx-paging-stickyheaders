package com.example.pagingtest.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pagingtest.repository.CheeseRepository

class CheeseViewModelFactory (private val cheeseRepository: CheeseRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = CheeseViewModel(cheeseRepository) as T
}
