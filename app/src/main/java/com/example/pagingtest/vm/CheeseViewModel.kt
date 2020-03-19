package com.example.pagingtest.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.pagingtest.data.Cheese
import com.example.pagingtest.ioThread
import com.example.pagingtest.repository.CheeseRepository
import io.reactivex.disposables.CompositeDisposable

class CheeseViewModel(private val cheeseRepository: CheeseRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var pagedListCheese = MutableLiveData<PagedList<Cheese>>()

    fun getCheese() {
        compositeDisposable.add(cheeseRepository.fetchOrGetCheese()
            .subscribe({
                pagedListCheese.value = it
            }, { it.printStackTrace() })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun remove(cheese: Cheese) = ioThread {
        cheeseRepository.delete(cheese)
    }
}
