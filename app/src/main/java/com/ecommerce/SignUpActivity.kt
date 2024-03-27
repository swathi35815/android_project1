package com.ecommerce

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.ecommerce.databaseUser.UsersDB
import com.ecommerce.databaseUser.UsersEntity
import com.example.e_commerce.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var db: UsersDB = UsersDB.getDatabase(this)

        var editTextFullName = findViewById<EditText>(R.id.editTextSignUpPageFullName)
        var editTextEmailId = findViewById<EditText>(R.id.editTextSignUpPageSignUpEmail)
        var editTextPhoneNum = findViewById<EditText>(R.id.editTextSignUpPagePhoneNum)
        var editTextPassword = findViewById<EditText>(R.id.editTextSignUpPageSignUpPassword)

        var buttonSignUp = findViewById<Button>(R.id.buttonSignUpPageSignUp)
        buttonSignUp.setOnClickListener {
            var userFullName = editTextFullName.text.toString()
            var userEmailId = editTextEmailId.text.toString()
            var userPhoneNum = editTextPhoneNum.text.toString()
            var userPassword = editTextPassword.text.toString()
            if(userFullName.isEmpty()
                || userEmailId.isEmpty()
                || userPhoneNum.isEmpty()
                || userPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all the details!", Toast.LENGTH_SHORT).show()
                if(!isPhoneNumberValid(userPhoneNum)){
                    Toast.makeText(this, "Please enter valid phone number", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                GlobalScope.launch {
                    var user = UsersEntity()
                    user.userFullName = userFullName
                    user.userEmailId = userEmailId
                    user.userPhoneNumber = userPhoneNum.toLong()
                    user.userPassword = userPassword
                    var verifyUser = db.usersDao().getUserByEmail(userEmailId)
                    if (verifyUser == null) {
                        db.usersDao().saveData(user)
                        launch(Dispatchers.Main) {
                            Toast.makeText(
                                this@SignUpActivity,
                                "Registered Successfully! Please Login",
                                Toast.LENGTH_SHORT
                            ).show()
                            var loginIntent = Intent(this@SignUpActivity, LoginActivity::class.java)
                            startActivity(loginIntent)
                        }
                    } else {
                        launch(Dispatchers.Main) {
                            Toast.makeText(
                                this@SignUpActivity,
                                "Email ID already exists!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        var buttonSignIn = findViewById<Button>(R.id.buttonSignUpPageSignIn)
        buttonSignIn.setOnClickListener {
            var intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }
    }

    private fun isPhoneNumberValid(userPhoneNum: String): Boolean {
        return try{
            userPhoneNum.toLong()
            true
        }catch (e: NumberFormatException){
            false
        }
    }
}