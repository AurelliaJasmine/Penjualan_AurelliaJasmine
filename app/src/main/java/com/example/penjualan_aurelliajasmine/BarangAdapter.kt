package com.example.penjualan_aurelliajasmine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.penjualan_aurelliajasmine.Room.TbBarang
import kotlinx.android.synthetic.main.activity_barang_adapter.view.*
import kotlinx.android.synthetic.main.activity_barang_adapter.view.CV_adapter
import kotlinx.android.synthetic.main.activity_edit.view.*

class BarangAdapter (private val barang :ArrayList<TbBarang>,private val listener: OnAdapterListener)
    : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>(){
    class BarangViewHolder(val view: View) : RecyclerView.ViewHolder(view){
    }

    interface OnAdapterListener {
        fun onClick (tbBarang: TbBarang)
        fun onUpdate(tbBarang: TbBarang)
        fun onDelete(tbBarang: TbBarang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        return BarangViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_barang_adapter,parent,false)
        )
    }

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val TbBrg = barang[position]
        holder.view.txtNama_Brg.text = TbBrg.nama_brg
        holder.view.txtHarga_Brg.text = TbBrg.hrg_brg.toString()
        holder.view.CV_adapter.setOnClickListener{
            listener.onClick(TbBrg)
        }
        holder.view.ic_edit.setOnClickListener {
            listener.onUpdate(TbBrg)
        }
        holder.view.ic_delete.setOnClickListener {
            listener.onDelete(TbBrg)
        }
    }

    override fun getItemCount() = barang.size

    fun setData(list: List <TbBarang>){
        barang.clear()
        barang.addAll(list)
        notifyDataSetChanged()
    }
}