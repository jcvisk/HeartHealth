package com.startechnology.apphealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.startechnology.apphealth.databinding.ActivityAccountRecoveryBinding

class AccountRecoveryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountRecoveryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAccountRecoveryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendEmailAppCompatButton.setOnClickListener {
            val emailAddress = binding.emailEditText.text.toString()
            Firebase.auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    val intent = Intent(this, SignInActivity::class.java)
                    this.startActivity(intent)
                }else{
                    Toast.makeText(this, "Ingrese el email de una cuenta válida.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}