/**
 * Activity class for the sign up screen.
 * Allows users to register by providing personal details.
 */
package com.ecommerce

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ecommerce.databaseUser.UsersDB
import com.ecommerce.databaseUser.UsersEntity
import com.example.e_commerce.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

/**
 * AppCompatActivity subclass representing the sign up screen.
 */
class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display for immersive experience
        enableEdgeToEdge()

        // Set the layout for the activity
        setContentView(R.layout.activity_sign_up)

        // Apply window insets listener to adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtain an instance of the UsersDB
        val db: UsersDB = UsersDB.getDatabase(this)

        // Find views by their IDs
        val editTextFullName = findViewById<EditText>(R.id.editTextSignUpPageFullName)
        val editTextEmailId = findViewById<EditText>(R.id.editTextSignUpPageSignUpEmail)
        val editTextPhoneNum = findViewById<EditText>(R.id.editTextSignUpPagePhoneNum)
        val editTextPassword = findViewById<EditText>(R.id.editTextSignUpPageSignUpPassword)

        // Find view for the sign up button and set click listener
        val buttonSignUp = findViewById<Button>(R.id.buttonSignUpPageSignUp)
        buttonSignUp.setOnClickListener {
            val userFullName = editTextFullName.text.toString()
            val userEmailId = editTextEmailId.text.toString()
            val userPhoneNum = editTextPhoneNum.text.toString()
            val userPassword = editTextPassword.text.toString()
            if (userFullName.isEmpty() || userEmailId.isEmpty() || userPhoneNum.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all the details!", Toast.LENGTH_SHORT).show()
                if (!isPhoneNumberValid(userPhoneNum)) {
                    Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Use coroutine for asynchronous database operation
                GlobalScope.launch {
                    val user = UsersEntity().apply {
                        this.userFullName = userFullName
                        this.userEmailId = userEmailId
                        this.userPhoneNumber = userPhoneNum.toLong()
                        this.userPassword = userPassword
                    }
                    val verifyUser = db.usersDao().getUserByEmail(userEmailId)
                    if (verifyUser == null) {
                        db.usersDao().saveData(user)
                        launch(Dispatchers.Main) {
                            Toast.makeText(
                                this@SignUpActivity,
                                "Registered Successfully! Please Login",
                                Toast.LENGTH_SHORT
                            ).show()
                            val loginIntent = Intent(this@SignUpActivity, LoginActivity::class.java)
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

        // Find view for the sign in button and set click listener
        val buttonSignIn = findViewById<Button>(R.id.buttonSignUpPageSignIn)
        buttonSignIn.setOnClickListener {
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }
    }

    /**
     * Checks if the provided phone number is valid.
     *
     * @param userPhoneNum The phone number to validate.
     * @return True if the phone number is valid, false otherwise.
     */
    private fun isPhoneNumberValid(userPhoneNum: String): Boolean {
        return try {
            userPhoneNum.toLong()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}
