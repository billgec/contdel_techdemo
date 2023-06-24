package at.fhjoanneum.lanfinderkotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import at.fhjoanneum.lanfinderkotlin.R
import com.google.firebase.auth.FirebaseAuth

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        Handler().postDelayed(
            {
                //check if user already loggedIn
                //if yes => call HomeActivity
                // if no => call LoginActivity
                if(FirebaseAuth.getInstance().currentUser != null){
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("user_info", FirebaseAuth.getInstance().currentUser!!.email)
                    startActivity(intent)
                    //splash activity should be closed and not shown again if the back-button is clicked!
                    finish()
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                    //splash activity should be closed and not shown again if the back-button is clicked!
                    finish()
                }
            }, 2000)
    }
}