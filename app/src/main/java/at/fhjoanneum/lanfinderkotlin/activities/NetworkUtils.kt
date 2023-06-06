package at.fhjoanneum.lanfinderkotlin.activities

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.view.ViewGroup
import android.widget.Button
import at.fhjoanneum.lanfinderkotlin.R

object NetworkUtils {
    private var isDialogShown = true

    fun openNetworkErrorDialog(context: Context) {
        if (isDialogShown) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_network_error)

            // Customize the dialog attributes as desired
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            // Set the dialog as modal to block the view behind
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)

            val btnRetry = dialog.findViewById<Button>(R.id.btn_retry)
            val btnOffline = dialog.findViewById<Button>(R.id.btn_offline)

            btnRetry.setOnClickListener {
                if (isNetworkConnected(context)) {
                    // If there is a connection, close the dialog
                    dialog.dismiss()
                } else {
                    // Show message or perform any desired action for no connection
                }
            }

            btnOffline.setOnClickListener {
                // Close the dialog when the "Offline" button is pressed
                dialog.dismiss()
                isDialogShown = false
            }

            // Show the dialog
            dialog.show()
        }
    }

    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
