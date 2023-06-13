package at.fhjoanneum.lanfinderkotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import at.fhjoanneum.lanfinderkotlin.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val tvLogin : TextView = findViewById(R.id.tv_register_login)
        tvLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        setupActionBar()
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
}