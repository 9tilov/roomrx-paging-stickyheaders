package com.example.pagingtest.repository

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.example.pagingtest.data.Cheese
import com.example.pagingtest.data.CheeseDataSource
import io.reactivex.Observable

class CheeseRepository( private val cheeseDataSource: CheeseDataSource) {

    fun fetchOrGetCheese(): Observable<PagedList<Cheese>> = RxPagedListBuilder(
        cheeseDataSource.getCheeses(),
        PagedList.Config.Builder()
            .setPageSize(15)
            .setInitialLoadSizeHint(30)
            .setPrefetchDistance(5)
            .setEnablePlaceholders(false)
            .build())
        .buildObservable()

    fun delete(cheese: Cheese) {
        cheeseDataSource.delete(cheese)
    }
}
