package com.example.penjualan_aurelliajasmine.Room

import androidx.room.*

@Dao
interface TbBarangDAO {
    @Insert
    fun addBrg (tbBarang: TbBarang)

    @Update
    fun upateBrg (tbBarang: TbBarang)

    @Delete
    fun deleteBrg (tbBarang: TbBarang)

    @Query("SELECT*FROM TbBarang")
    fun getBrg(): List<TbBarang>

    @Query("SELECT*FROM TbBarang WHERE id_brg=:tbBrg_id")
    fun tampilBrg(tbBrg_id: Int): List<TbBarang>
}