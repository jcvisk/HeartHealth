package com.startechnology.apphealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.startechnology.apphealth.databinding.ActivitySignUpBinding
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.submitRegister.setOnClickListener {
            val mEmail = binding.userRegister.text.toString()
            val mPassword = binding.passwordRegister.text.toString()
            val mPasswordConfirm = binding.confirmPassword.text.toString()

            val passwordRegex = Pattern.compile("^"+
                    "(?=.*[-@#$%^&+=])" + //al menos 1 caracter especial
                    ".{6,}" +   //al menos 6 caracteres
                    "$")

            if (mEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
                Toast.makeText(baseContext, "Formato no válido.",
                    Toast.LENGTH_SHORT).show()
            }else if(mPassword.isEmpty() || !passwordRegex.matcher(mPassword).matches()){
                Toast.makeText(baseContext, "La contraseña es debil. \nDebe contener al menos un caracter epecial.\nEl tamaño minimo es de 6 caracteres.",
                    Toast.LENGTH_LONG).show()
            }else if (mPassword != mPasswordConfirm){
                Toast.makeText(baseContext, "Las contraseñas no coinciden.",
                    Toast.LENGTH_SHORT).show()
            }else{
                createAccount(mEmail,mPassword)
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            if(currentUser.isEmailVerified){
                reload();
            }else{
                val intent = Intent(this, CheckEmailActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun createAccount(email:String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, CheckEmailActivity::class.java)
                    this.startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun reload(){
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }

    private fun backToLogin(){
        val intent = Intent(this, SignInActivity::class.java)
        this.startActivity(intent)
    }
}