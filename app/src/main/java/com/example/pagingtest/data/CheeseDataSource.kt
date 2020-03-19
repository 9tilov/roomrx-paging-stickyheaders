package com.example.pagingtest.data

import com.example.pagingtest.data.CheeseDao

class CheeseDataSource(private val cheeseDao: CheeseDao) {
    fun getCheeses() = cheeseDao.allCheesesByName()

    fun delete(cheese: Cheese) = cheeseDao.delete(cheese)
}
