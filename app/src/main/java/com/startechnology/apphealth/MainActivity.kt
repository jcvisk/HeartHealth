package com.startechnology.apphealth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.legacy.coreui.R
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.startechnology.apphealth.databinding.ActivityMainBinding
import com.startechnology.apphealth.db.AppDataBase
import com.startechnology.apphealth.db.DatosSalud
import com.startechnology.apphealth.db.DatosSaludAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        /**
         * Base de datos
         */
        var listaItems = emptyList<DatosSalud>()
        val database = AppDataBase.getDatabase(this)
        database.datosSalud().getAll().observe(this, Observer {
            listaItems = it
            val adapter = DatosSaludAdapter(this, listaItems)

            listaDatos.adapter = adapter
        })

        /**
         * Button para abrir el form
         */
        binding.goForm.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }

        /**
         * Hace un intent y manda el objeto lleno
         */
        listaDatos.setOnItemClickListener {parent, view, position, id ->
            val intent = Intent(this, FormActivity::class.java)
            intent.putExtra("datosSalud", listaItems[position])
            startActivity(intent)
        }

        /**
         * Hace un intent a la vista de reportes
         */
        binding.btnMenuReportes.setOnClickListener { view ->
            val intent = Intent(this, ReporteActivity::class.java)
            startActivity(intent)
        }

        /**
         * Hace un intent a la vista del perfil
         */
        binding.imageUserNav.setOnClickListener { view ->
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        /**
         * Muestra el ultimo registro en la tabla azul
         * Muestra el staus (hipertencion, Hipotencion, Normal)
         */
        database.datosSalud().getLast().observe(this, Observer {
            val ultimoRegistro = it

            if (ultimoRegistro != null){

                /*var sistolica:Int = ultimoRegistro.sistolica
                var diastolica:Int = ultimoRegistro.sistolica
                var pulso:Int = ultimoRegistro.sistolica*/

                binding.sistolica.text = ultimoRegistro.sistolica.toString()
                binding.diastolica.text = ultimoRegistro.diastolica.toString()
                binding.pulso.text = ultimoRegistro.pulso.toString()

                /*if (sistolica < 100 || diastolica < 100 || pulso < 100){
                    binding.txtStatus.text = "Hipotencion"
                }else if (sistolica > 120 || diastolica > 120 || pulso > 120){
                    binding.txtStatus.text = "Hipertencion"
                }else{
                    binding.txtStatus.text = "Normal"
                }*/
            }else{
                binding.sistolica.text = "0"
                binding.diastolica.text = "0"
                binding.pulso.text = "0"

                /*binding.txtStatusSis.text = "X"
                binding.txtStatusDia.text = "X"
                binding.txtStatusPulso.text = "X"*/
            }
        })

    }
}


