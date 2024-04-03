/**
 * Activity class for the login screen.
 * Handles user authentication and navigation to other screens.
 */
package com.ecommerce

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ecommerce.databaseUser.UsersDB
import com.example.e_commerce.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * AppCompatActivity subclass representing the login screen.
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display for immersive experience
        enableEdgeToEdge()

        // Set the layout for the activity
        setContentView(R.layout.activity_login)

        // Apply window insets listener to adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtain an instance of the UsersDB
        val db: UsersDB = UsersDB.getDatabase(this)
        val h = Handler()
        val sp: SharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE)

        // Find views by their IDs
        val editTextEmailId = findViewById<EditText>(R.id.editTextLoginPageLoginEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextLoginPageLoginPassword)

        // Auto-fill Email ID and Password if available
        editTextEmailId.setText(sp.getString("keyEmailId", ""))
        editTextPassword.setText(sp.getString("keyPassword", ""))

        // Find view for the login button and set click listener
        val buttonLogin = findViewById<Button>(R.id.buttonLoginPageLogin)
        buttonLogin.setOnClickListener {
            val emailId = editTextEmailId.text.toString()
            val password = editTextPassword.text.toString()
            val editor = sp.edit()
            if (emailId.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter credentials", Toast.LENGTH_SHORT).show()
            } else {
                // Use coroutine for asynchronous database query
                GlobalScope.launch {
                    val verifyCredentials = db.usersDao().getUserByEmailAndPassword(emailId, password)
                    h.post {
                        if (verifyCredentials != null) {
                            val rememberPasswordCheckBox = findViewById<CheckBox>(R.id.checkBoxLoginPageRememberPassword)
                            if (rememberPasswordCheckBox.isChecked) {
                                editor.putString("keyEmailId", emailId)
                                editor.putString("keyPassword", password)
                            } else {
                                editor.remove("keyEmailId")
                                editor.remove("keyPassword")
                            }
                            editor.apply()
                            Toast.makeText(this@LoginActivity, "Logged in Successfully!", Toast.LENGTH_SHORT).show()
                            val dashboardIntent = Intent(this@LoginActivity, DashboardActivity::class.java)
                            dashboardIntent.putExtra("keyEmailId", emailId)
                            startActivity(dashboardIntent)
                        } else {
                            Toast.makeText(this@LoginActivity, "Invalid Email ID or Password", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        // Find view for the forgot password button and set click listener
        val buttonForgotPassword = findViewById<Button>(R.id.buttonLoginPageForgotPassword)
        buttonForgotPassword.setOnClickListener {
            Toast.makeText(this, "Think!", Toast.LENGTH_SHORT).show()
        }

        // Find view for the sign up button and set click listener
        val buttonSignUp = findViewById<Button>(R.id.buttonLoginPageSignUp)
        buttonSignUp.setOnClickListener {
            val intentSignUp = Intent(this, SignUpActivity::class.java)
            startActivity(intentSignUp)
        }
    }
}
