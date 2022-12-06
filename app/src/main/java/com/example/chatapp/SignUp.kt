package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class SignUp : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth
    private  lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        edtName =findViewById(R.id.name)
        edtEmail =findViewById(R.id.email)
        edtPassword =findViewById(R.id.password)
        btnSignUp =findViewById(R.id.signup)

        btnSignUp.setOnClickListener {
            val name =  edtName.text.toString()
            val email =     edtEmail.text.toString()
            val password =     edtPassword.text.toString()

            signup(name,email,password)
        }
    }
    private fun signup(name:String,email: String ,password: String){
        //logic of creating user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                     //code for jumping to home
                    addUserToTheDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(  this@SignUp," Some Error Occurred", Toast.LENGTH_SHORT).show()

                }
            }
    }
   private fun addUserToTheDatabase(name:String,email: String,uid:String){

       mDbRef = FirebaseDatabase.getInstance().getReference()
       mDbRef.child("user").child(uid).setValue(User(name,email,uid))
   }



}