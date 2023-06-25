package at.fhjoanneum.lanfinderkotlin.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import at.fhjoanneum.lanfinderkotlin.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

/*
 * ForgotPasswordActivity
 * Activity for handling password reset functionality.
 * May 25, 2023
 */
class ForgotPasswordActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        setupActionBar()

        val btnForgotPasswordSubmit: Button = findViewById(R.id.btn_forgotpassword_submit)

        btnForgotPasswordSubmit.setOnClickListener {
            val email = validateEmail()
            if(email != null) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            showCustomSnackbar(getString(R.string.an_email_was_sent_to_your_email_address), false)
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        } else {
                            showCustomSnackbar(task.exception!!.message.toString(), true)
                        }
                    }
            }
        }
    }

    private fun setupActionBar() {
        val toolbarRegisterActivity : Toolbar = findViewById(R.id.toolbar_forgotpassword_toolbar)
        setSupportActionBar(toolbarRegisterActivity)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbarRegisterActivity.setNavigationOnClickListener{ onBackPressedDispatcher.onBackPressed()}
    }

    private fun validateEmail(): String? {
        val etEmail : EditText = findViewById(R.id.et_forgotpassword_email)
        val email : String = etEmail.text.toString().trim { it <= ' ' }

        if (email.isEmpty()) {
            findViewById<TextInputLayout>(R.id.til_forgotpassword_email)
                .error = getString(R.string.please_enter_your_email_address)
            return null
        }

        return email
    }
}