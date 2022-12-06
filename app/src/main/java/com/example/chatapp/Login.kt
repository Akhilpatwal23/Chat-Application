package com.example.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin:  Button
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mAuth=FirebaseAuth.getInstance()
        edtEmail=findViewById(R.id.email)
        edtPassword=findViewById(R.id.password)
        btnLogin=findViewById(R.id.login)
        btnSignUp= findViewById(R.id.signup)

            btnSignUp.setOnClickListener{
           val intent = Intent(this, SignUp::class.java)
           startActivity(intent)
        }
        //btnlogin pressed
        btnLogin.setOnClickListener {
           val email=edtEmail.text.toString()
            val password= edtPassword.text.toString()

            login(email,password)
        }

    }
  private fun login(email: String, password: String){

     //logic for signin
      mAuth.signInWithEmailAndPassword(email, password)
          .addOnCompleteListener(this) { task ->
              if (task.isSuccessful) {
                  // logic for logging in user
                  val intent = Intent(this@Login, MainActivity::class.java)
                  finish()
                  startActivity(intent)

              } else {
                  // If sign in fails, display a message to the user.
                  Toast.makeText(  this@Login," User does not exist", Toast.LENGTH_SHORT).show()
              }
          }

  }

}