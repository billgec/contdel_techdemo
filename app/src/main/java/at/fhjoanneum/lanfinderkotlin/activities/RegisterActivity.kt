package at.fhjoanneum.lanfinderkotlin.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import at.fhjoanneum.lanfinderkotlin.R
import at.fhjoanneum.lanfinderkotlin.restapi.models.User
import at.fhjoanneum.lanfinderkotlin.restapi.services.UserController
import com.google.firebase.auth.FirebaseAuth

/*
 * RegisterActivity
 * Activity for user registration.
 * May 25, 2023
 */
class RegisterActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val tvLogin : TextView = findViewById(R.id.tv_register_login)
        tvLogin.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        setupActionBar()

        val btnRegister : Button = findViewById(R.id.btn_register_register)
        btnRegister.setOnClickListener {
            registerNewUser()
        }
    }

    private fun setupActionBar() {
        val toolbarRegisterActivity : Toolbar = findViewById(R.id.toolbar_register_activity)
        setSupportActionBar(toolbarRegisterActivity)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbarRegisterActivity.setNavigationOnClickListener{ onBackPressedDispatcher.onBackPressed()}
    }

    fun registerNewUser() {
        if(validateUserInformation()) {
            val etUserName : EditText = findViewById(R.id.et_register_username)
            val etEmail : EditText = findViewById(R.id.et_register_email)
            val etPassword : EditText = findViewById(R.id.et_register_password)

            val userName : String = etUserName.text.toString().trim { it <= ' ' }
            val email : String = etEmail.text.toString().trim { it <= ' ' }
            val password : String = etPassword.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        val firebaseUser = task.result!!.user!!
                        UserController.createUser(User(userName, email))
                        showCustomSnackbar(getString(R.string.a_new_user_has_been_created_with_firebase), false)
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        showCustomSnackbar(task.exception!!.toString(), true)
                    }
                }
        }
    }

    fun userRegistrationSuccess(){
        Toast.makeText(this, getString(R.string.you_are_registered_successfully), Toast.LENGTH_SHORT).show()
    }

    fun validateUserInformation(): Boolean {
        val etUserName : EditText = findViewById(R.id.et_register_username)
        val etEmail : EditText = findViewById(R.id.et_register_email)
        val etPassword : EditText = findViewById(R.id.et_register_password)
        val etConfirmPassword : EditText = findViewById(R.id.et_register_confirmpassword)
        val cbTermsAndCondition : CheckBox = findViewById(R.id.cb_register_termsandcondition)

        val userName : String = etUserName.text.toString().trim { it <= ' ' }
        val email : String = etEmail.text.toString().trim { it <= ' ' }
        val password : String = etPassword.text.toString().trim { it <= ' ' }
        val confirmPassword : String = etConfirmPassword.text.toString().trim { it <= ' ' }

        val returnValue = when {
            userName.isEmpty() -> {
                showCustomSnackbar(getString(R.string.please_enter_first_name), true)
                false
            }
            email.isEmpty() -> {
                showCustomSnackbar(getString(R.string.please_enter_email), true)
                false
            }
            password.isEmpty() -> {
                showCustomSnackbar(getString(R.string.please_enter_password), true)
                false
            }
            confirmPassword.isEmpty() -> {
                showCustomSnackbar(getString(R.string.please_enter_confirm_password), true)
                false
            }
            password != confirmPassword -> {
                showCustomSnackbar(getString(R.string.password_and_confirm_password_do_not_match), true)
                false
            }
            !cbTermsAndCondition.isChecked -> {
                showCustomSnackbar(getString(R.string.please_agree_terms_and_conditions), true)
                false
            }
            else -> true
        }

        return returnValue
    }
}