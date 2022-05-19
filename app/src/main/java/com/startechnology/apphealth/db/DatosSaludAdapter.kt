package com.startechnology.apphealth.db

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.startechnology.apphealth.R
import kotlinx.android.synthetic.main.datosalud_item.view.*

class DatosSaludAdapter(private val mContext: Context, private val listaItems: List<DatosSalud>) : ArrayAdapter<DatosSalud>(mContext,0, listaItems){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layout = LayoutInflater.from(mContext).inflate(R.layout.datosalud_item, parent,false)

        val item = listaItems[position]

        layout.fecha.text = item.fecha
        layout.sis.text = item.sistolica.toString()
        layout.dia.text = item.diastolica.toString()
        layout.pulso.text = item.pulso.toString()

        return layout
    }
}