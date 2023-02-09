package com.example.penjualan_aurelliajasmine.Room

import android.content.Context
import androidx.room.*

@Database (entities = [TbBarang::class], version = 1)
abstract class dbPenjualan : RoomDatabase() {
    abstract fun tbBarangDAO() : TbBarangDAO

    companion object{
        @Volatile private var instance: dbPenjualan? = null
        private val LOCK = Any()
        operator fun invoke (context: Context) = instance?: synchronized(LOCK){
            instance?: builDatabase(context).also{
                instance = it
            }
        }

        private fun builDatabase(context: Context) = Room.databaseBuilder (
            context.applicationContext,
            dbPenjualan::class.java,
            "penjualan.db"
        ).build()
    }
}