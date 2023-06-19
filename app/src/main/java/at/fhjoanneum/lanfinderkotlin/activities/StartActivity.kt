package at.fhjoanneum.lanfinderkotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import at.fhjoanneum.lanfinderkotlin.R

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        startActivity(Intent(this, LoginActivity::class.java))
    }
}