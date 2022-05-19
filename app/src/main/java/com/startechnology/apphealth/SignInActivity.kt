package com.startechnology.apphealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.startechnology.apphealth.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.submitLogin.setOnClickListener {
            val mEmail = binding.userLogin.text.toString()
            val mPassword = binding.passwordLogin.text.toString()
            when{
                mEmail.isEmpty() || mPassword.isEmpty()->{
                    Toast.makeText(baseContext, "Usuario o contraseña incorrectos.",
                        Toast.LENGTH_SHORT).show()
                }else ->{
                signIn(mEmail,mPassword)
            }
            }
        }

        binding.signUpTV.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            this.startActivity(intent)
        }

        binding.forgetPassword.setOnClickListener{
            val intent = Intent(this, AccountRecoveryActivity::class.java)
            this.startActivity(intent)
        }

    }

    /**
     * TODO: Todo con respecto al login
     */
    private fun signIn(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithEmail:success")
                    reload()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Usuario o contraseña incorrectos.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun reload(){
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
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
}