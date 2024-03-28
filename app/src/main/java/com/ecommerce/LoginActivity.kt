package com.ecommerce

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.ecommerce.databaseUser.UsersDB
import com.example.e_commerce.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var db: UsersDB = UsersDB.getDatabase(this)
        var h = Handler()
        var sp : SharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE)

        var editTextEmailId = findViewById<EditText>(R.id.editTextLoginPageLoginEmail)
        var editTextPassword = findViewById<EditText>(R.id.editTextLoginPageLoginPassword)

        // Auto fill Email ID and Password
        editTextEmailId.setText(sp.getString("keyEmailId", ""))
        editTextPassword.setText(sp.getString("keyPassword", ""))


        var buttonLogin = findViewById<Button>(R.id.buttonLoginPageLogin)
        buttonLogin.setOnClickListener {
            var emailId = editTextEmailId.text.toString()
            var password = editTextPassword.text.toString()
            var editor = sp.edit()
            if(emailId.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter credentials", Toast.LENGTH_SHORT).show()
            }
            else{
                GlobalScope.launch {
                    var verifyCredentials = db.usersDao().getUserByEmailAndPassword(emailId, password)
                    h.post {
                        if(verifyCredentials != null){
                            var rememberPasswordCheckBox = findViewById<CheckBox>(R.id.checkBoxLoginPageRememberPassword)
                            if(rememberPasswordCheckBox.isChecked) {
                                editor.putString("keyEmailId", emailId)
                                editor.putString("keyPassword", password)
                            }
                            else{
                                editor.remove("keyEmailId")
                                editor.remove("keyPassword")
                            }
                            editor.apply()
                            Toast.makeText(this@LoginActivity, "Logged in Successfully!", Toast.LENGTH_SHORT).show()
                            var dashboardIntent = Intent(this@LoginActivity, DashboardActivity::class.java)
                            dashboardIntent.putExtra("keyEmailId", emailId)
                            startActivity(dashboardIntent)
                        }
                        else{
                            Toast.makeText(this@LoginActivity, "Invalid Email ID or Password", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        var buttonForgotPassword = findViewById<Button>(R.id.buttonLoginPageForgotPassword)
        buttonForgotPassword.setOnClickListener {
            Toast.makeText(this, "Think!", Toast.LENGTH_SHORT).show()
        }

        var buttonSignUp = findViewById<Button>(R.id.buttonLoginPageSignUp)
        buttonSignUp.setOnClickListener {
            var intentSignUp = Intent(this, SignUpActivity::class.java)
            startActivity(intentSignUp)
        }
    }
}