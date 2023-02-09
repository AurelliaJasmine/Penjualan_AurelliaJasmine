package com.example.penjualan_aurelliajasmine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.penjualan_aurelliajasmine.Room.Constant
import com.example.penjualan_aurelliajasmine.Room.TbBarang
import com.example.penjualan_aurelliajasmine.Room.dbPenjualan
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    val db by lazy { dbPenjualan(this) }
    private var tbBrgId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        tombolPerintah()
        tbBrgId = intent.getIntExtra("intent_id",tbBrgId)
        Toast.makeText(this,tbBrgId.toString(),Toast.LENGTH_SHORT).show()
    }

    private fun setupView() {

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type",0)
        when(intentType) {
            Constant.TYPE_CREATE -> {
                btnUpdate.visibility = View.GONE
            }
            Constant.TYPE_READ   -> {
                btnSave.visibility   = View.GONE
                btnUpdate.visibility = View.GONE
                tampilbarang()
            }
            Constant.TYPE_UPDATE -> {
                btnSave.visibility   = View.GONE
                etId_brg.visibility  = View.GONE
                tampilbarang()

            }
        }
    }

    private fun tampilbarang() {
        tbBrgId = intent.getIntExtra("intent_id",0)
        CoroutineScope(Dispatchers.IO).launch {
            val barang = db.tbBarangDAO().tampilBrg(tbBrgId)[0]
            val dataId : String = barang.id_brg.toString()
            val dataharga : String = barang.hrg_brg.toString()
            val datastok : String = barang.stok.toString()
            etId_brg.setText(dataId)
            etnama_brg.setText(barang.nama_brg)
            etharga_brg.setText(dataharga)
            etstok_brg.setText(datastok)
        }
    }

    private fun tombolPerintah() {
        btnSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbBarangDAO().addBrg(
                    TbBarang(etId_brg.text.toString().toInt(),
                             etnama_brg.text.toString(),
                             etharga_brg.text.toString().toInt(),
                             etstok_brg.text.toString().toInt())
                )
                finish()
            }
        }
        btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbBarangDAO().upateBrg(
                    TbBarang(tbBrgId,
                            etnama_brg.text.toString(),
                            etharga_brg.text.toString().toInt(),
                            etstok_brg.text.toString().toInt())
                )
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}