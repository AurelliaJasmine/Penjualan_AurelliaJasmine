package com.example.penjualan_aurelliajasmine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.penjualan_aurelliajasmine.Room.Constant
import com.example.penjualan_aurelliajasmine.Room.TbBarang
import com.example.penjualan_aurelliajasmine.Room.dbPenjualan
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { dbPenjualan(this) }
    lateinit var barangAdapter: BarangAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        halEdit()
        setupRV()
    }

    private fun setupRV() {
        barangAdapter = BarangAdapter(arrayListOf(),object : BarangAdapter.OnAdapterListener{
            override fun onClick(tbBarang: TbBarang) {
                intentEdit(tbBarang.id_brg,Constant.TYPE_READ)
            }

            override fun onUpdate(tbBarang: TbBarang) {
                intentEdit(tbBarang.id_brg,Constant.TYPE_UPDATE)
            }

            override fun onDelete(tbBarang: TbBarang) {
                hapusBarang(tbBarang)

            }
        })
        RV_List.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = barangAdapter
        }
    }

    private fun intentEdit(tbBrgId: Int, intentType: Int) {
        startActivity(
            Intent(applicationContext,EditActivity::class.java)
                .putExtra("intent_id",tbBrgId)
                .putExtra("intent_type",intentType)
        )

    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val barang = db.tbBarangDAO().getBrg()
            Log.d("MainActivity","dbResponse:$barang")
            withContext(Dispatchers.Main){
                barangAdapter.setData(barang)
            }
        }
    }

    private fun halEdit() {
        btnAdd.setOnClickListener {
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    private fun hapusBarang(tbBarang: TbBarang) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin Hapus${tbBarang.nama_brg}?")
            setNegativeButton("No") {dialogInterface,i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Yes") {dialogInterface,i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbBarangDAO().deleteBrg(tbBarang)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }
        alertDialog.show()
    }
}