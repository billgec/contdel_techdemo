package at.fhjoanneum.lanfinderkotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import at.fhjoanneum.lanfinderkotlin.R

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        setupActionBar()
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
}