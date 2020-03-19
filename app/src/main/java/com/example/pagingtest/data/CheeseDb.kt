package com.example.pagingtest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pagingtest.ioThread

/**
 * Singleton database object. Note that for a real app, you should probably use a Dependency
 * Injection framework or Service Locator to create the singleton database.
 */
@Database(entities = [Cheese::class], version = 1)
abstract class CheeseDb : RoomDatabase() {
    abstract fun cheeseDao(): CheeseDao

    companion object {
        private const val SIZE = 5000
        private var instance: CheeseDb? = null

        @Synchronized
        fun get(context: Context): CheeseDb {
            if (instance == null) {
                instance = Room.databaseBuilder(
                        context.applicationContext,
                        CheeseDb::class.java, "CheeseDatabase"
                    )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            fillInDb(
                                context.applicationContext
                            )
                        }
                    }).build()
            }
            return instance!!
        }

        /**
         * fill database with list of cheeses
         */
        private fun fillInDb(context: Context) {
            // inserts in Room are executed on the current thread, so we insert in the background
            ioThread {
                val data = ArrayList<Int>()
                for (i in 0..SIZE) {
                    data.add(i)
                }
                get(context).cheeseDao().insert(
                    data.map {
                        Cheese(
                            id = 0,
                            name = it.toString()
                        )
                    })
            }
        }
    }
}
