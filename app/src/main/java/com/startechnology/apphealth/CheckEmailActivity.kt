package com.startechnology.apphealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.startechnology.apphealth.databinding.ActivityCheckEmailBinding

class CheckEmailActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityCheckEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        val user = auth.currentUser

        binding.verificateEmail.setOnClickListener {
            val profileUpdates = userProfileChangeRequest {  }

            user!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
                //si el estado del usuario se actualizó corrrectamente
                if (task.isSuccessful){
                    if (user.isEmailVerified){
                        reload()
                    }else{
                        Toast.makeText(this, "Por favor, verifica tu correo electrónico.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.canceVerificationEmail.setOnClickListener {
            signOut()
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            if (currentUser.isEmailVerified){
                reload();
            }else{
                sendEmailVerification()
            }
        }
    }

    private fun sendEmailVerification(){
        val user = auth.currentUser
        user!!.sendEmailVerification().addOnCompleteListener(this){ task ->
            //si el correo se envío correctamente muestra este mensaje
            if (task.isSuccessful){
                Toast.makeText(this, "Se ha enviado un correo electrónico de verificación.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun reload(){
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }

    private fun signOut(){
        Firebase.auth.signOut()
        val intent = Intent(this, SignInActivity::class.java)
        this.startActivity(intent)
    }
}