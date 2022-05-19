package com.startechnology.apphealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.startechnology.apphealth.databinding.ActivityFormBinding
import com.startechnology.apphealth.db.AppDataBase
import com.startechnology.apphealth.db.DatosSalud
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant

class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Instancia de la db
         */
        val database = AppDataBase.getDatabase(this)


        /**
         * Si se recibe datosSalud semuestran los datos en los inputs
         */
        var idDatosSalud: Int? = null
        if (intent.hasExtra("datosSalud")){
            val datosSalud = intent.extras?.getSerializable("datosSalud") as DatosSalud

            inputSistolica.setText(datosSalud.sistolica.toString())
            inputDiastolica.setText(datosSalud.diastolica.toString())
            inputPulso.setText(datosSalud.pulso.toString())
            //inputFecha.setText(datosSalud.mes)
            inputNota.setText(datosSalud.nota)
            idDatosSalud = datosSalud.id
        }

        /**
         * Guarda si es nuevo registro o edita sies un registro nuevo
         */
        btnSave.setOnClickListener {
            if (inputSistolica.text.toString() != "" &&
                inputDiastolica.text.toString() != "" &&
                inputPulso.text.toString() != "" &&
                inputNota.text.toString() != ""){

                val sistolica = inputSistolica.text.toString().toInt()
                val diastolica = inputDiastolica.text.toString().toInt()
                val pulso = inputPulso.text.toString().toInt()
                var fecha = TextUtils.substring(Instant.now().toString(),0,10)
                var mes = TextUtils.substring(Instant.now().toString(),5,7)
                val nota = inputNota.text.toString()

                //var fecha = "2022-01-04"
                //var mes = "01"

                val datoSalud = DatosSalud(sistolica,diastolica,pulso,fecha,mes,nota)

                //si elid no es null edita
                if (idDatosSalud != null){
                    CoroutineScope(Dispatchers.IO).launch {
                        datoSalud.id = idDatosSalud

                        database.datosSalud().update(datoSalud)
                        this@FormActivity.finish()
                    }
                }
                //caso contrario crea
                else{
                    CoroutineScope(Dispatchers.IO).launch {
                        database.datosSalud().insertAll(datoSalud)

                        this@FormActivity.finish()
                    }
                }

            }else{
                Toast.makeText(baseContext, "El campo no puede esta vacío.",
                    Toast.LENGTH_SHORT).show()
            }

        }

        /**
         * Hace un intent a la vista de reportes
         */
        binding.btnMenuReportes.setOnClickListener { view ->
            val intent = Intent(this, ReporteActivity::class.java)
            startActivity(intent)
        }
        /**
         * Hace un intent al dashboard
         */
        binding.btnMenuDashboard.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}