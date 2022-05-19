package com.startechnology.apphealth

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.startechnology.apphealth.databinding.ActivityReporteBinding
import com.startechnology.apphealth.db.AppDataBase
import com.startechnology.apphealth.db.DatosSalud
import com.startechnology.apphealth.db.DatosSaludAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_reporte.*
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ReporteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReporteBinding

    //para el pdf
    //private lateinit var et_pdf_data : EditText
    //private val STORAGE_CODE = 1001

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReporteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Base de datos
         */
        var listaItems = emptyList<DatosSalud>()
        val database = AppDataBase.getDatabase(this)
        //adapter para el listview
        database.datosSalud().getAll().observe(this, Observer {
            listaItems = it
            val adapter = DatosSaludAdapter(this, listaItems)
            listaDatosReporte.adapter = adapter
        })

        /**
         * Hace un intent al dashboard
         */
        binding.btnMenuDashboard.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        /**
         * Hace un intent a la vista del perfil
         */
        binding.imageUserNav.setOnClickListener { view ->
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        btnSearch.setOnClickListener {
            if (inputMes.text.toString() != ""){
                val mes = inputMes.text.toString()

                database.datosSalud().getAllByMes(mes).observe(this, Observer {
                    listaItems = it
                    val adapter = DatosSaludAdapter(this, listaItems)
                    listaDatosReporte.adapter = adapter
                })

            }else{
                Toast.makeText(baseContext, "El campo no puede esta vacío.",
                    Toast.LENGTH_SHORT).show()
            }


        }


        /**
         * pARA EL PDF
         */

        /*et_pdf_data = findViewById(R.id.et_pdf_data)

        binding.btnDownloadPdf.setOnClickListener {
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
                    val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, STORAGE_CODE)
                }else{
                    savePDF()
                }
            }else{
                savePDF()
            }
        }*/
    }

    /*private fun savePDF() {
        val mDoc = Document()
        val mFileName= SimpleDateFormat("yyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())

        val mFilePath = Environment.getStorageDirectory().toString() + "/" + mFileName + ".pdf"

        try {
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
            mDoc.open()

            val data = et_pdf_data.toString().trim()
            mDoc.addAuthor("JCVG")
            mDoc.add(Paragraph(data))
            mDoc.close()

            Toast.makeText(this, "$mFileName.pdf\n ha sido guardado en\n $mFilePath", Toast.LENGTH_SHORT).show()

        }catch (e: Exception){
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            STORAGE_CODE->{
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    savePDF()
                }else{
                    Toast.makeText(this, "¡Permiso denegado!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }*/
}