package at.fhjoanneum.lanfinderkotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import at.fhjoanneum.lanfinderkotlin.R
import com.google.android.material.snackbar.Snackbar

open class BasicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)
    }

    fun showCustomSnackbar(message: String, errorMessage: Boolean) {
        val snackbar: Snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)

        if(errorMessage) {
            snackbar.view.setBackgroundColor(getColor(R.color.snackbar_error_color))
        } else {
            snackbar.view.setBackgroundColor(getColor(R.color.snackbar_success_color))
        }

        snackbar.show()
    }
}