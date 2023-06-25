package at.fhjoanneum.lanfinderkotlin.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import at.fhjoanneum.lanfinderkotlin.R
import com.google.android.material.snackbar.Snackbar

/*
 * BasicActivity
 * Base activity for other activities within LANFinder
 * Mai 25, 2023
 */
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