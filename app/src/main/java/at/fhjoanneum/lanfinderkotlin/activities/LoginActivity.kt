package at.fhjoanneum.lanfinderkotlin.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import at.fhjoanneum.lanfinderkotlin.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

/*
 * LoginActivity
 * Activity for user login.
 * May 25, 2023
 */
class LoginActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tvRegister : TextView = findViewById(R.id.tv_login_register)
        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        val tvForgotPassword : TextView = findViewById(R.id.tv_login_forgotpassword)
        tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        val btnLogin : Button = findViewById(R.id.btn_login_login)
        btnLogin.setOnClickListener {
            logInUser()
        }
    }

    fun logInUser(){
        val etEmailId : EditText = findViewById(R.id.et_login_email)
        val etPassword : EditText = findViewById(R.id.et_login_password)

        val emailID = etEmailId.text.toString().trim{ it <= ' '}
        val password = etPassword.text.toString().trim{ it <= ' '}

        if(validateLoginInformation(emailID, password)){
            FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(emailID, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        showCustomSnackbar(getString(R.string.you_are_logged_in), false);
                        userLoggedInSuccess(emailID)
                    } else {
                        showCustomSnackbar(task.exception!!.message.toString(), true);
                    }
                }
        }
    }

    private fun validateLoginInformation(emailID: String, password:String) : Boolean{
        return when {
            emailID.isEmpty() -> {
                (findViewById(R.id.til_login_email) as TextInputLayout).error = getString(R.string.please_enter_email)
                false
            }

            password.isEmpty() -> {
                (findViewById(R.id.til_login_password) as TextInputLayout).error = getString(R.string.please_enter_password)
                false
            }

            else -> true
        }
    }

    fun userLoggedInSuccess(email: String){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("user_info", email)
        startActivity(intent)
        finish()
    }
}