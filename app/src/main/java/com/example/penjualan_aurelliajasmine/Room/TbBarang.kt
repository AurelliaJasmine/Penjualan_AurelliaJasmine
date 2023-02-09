package com.example.penjualan_aurelliajasmine.Room

import androidx.room.*

@Entity
data class TbBarang (
    @PrimaryKey (autoGenerate = true)
    val id_brg: Int,
    val nama_brg:String,
    val hrg_brg:Int,
    val stok:Int
    )
