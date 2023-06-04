package at.fhjoanneum.lanfinderkotlin.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import at.fhjoanneum.lanfinderkotlin.R
import at.fhjoanneum.lanfinderkotlin.models.LanParty
import at.fhjoanneum.lanfinderkotlin.services.MockApiService
import com.google.android.gms.location.LocationServices
import java.util.Arrays
import java.util.Calendar
import java.util.Collections
import java.util.GregorianCalendar
import java.util.Objects

/**
 * This activity is used to create a new LAN.
 */
class CreateLan : AppCompatActivity() {
    //instances for date and time picker
    private var datePickerDialog: DatePickerDialog? = null
    private var timePickerDialog: TimePickerDialog? = null
    var calendarSet: GregorianCalendar? = null
    private val LOCATION_PERMISSION_REQUEST_CODE = 123
    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        /*
         * Action Bar settings (set logo to action bar and back button)
         */
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            title = "Create a LAN Party"
            setDisplayUseLogoEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }


        /*
         * Get the date and time from the date/time picker
         */
        val et_date = findViewById<EditText>(R.id.et_date)
        val c = GregorianCalendar()
        findViewById<View>(R.id.et_date).setOnClickListener { v: View? ->
            //create 2 dialogs for date and time
            datePickerDialog = DatePickerDialog(
                this@CreateLan,
                { view: DatePicker?, year1: Int, month1: Int, dayOfMonth: Int ->
                    et_date.setText(
                        String.format("%2d.%2d.%4d", dayOfMonth, month1 + 1, year1)
                            .replace(' ', '0')
                    )
                    calendarSet = GregorianCalendar(year1, month1, dayOfMonth)
                },
                c[Calendar.YEAR],
                c[Calendar.MONTH],
                c[Calendar.DAY_OF_MONTH]
            )
            timePickerDialog =
                TimePickerDialog(this@CreateLan, { view: TimePicker?, hourOfDay: Int, minute: Int ->
                    //set text of et_date to the time with double digits
                    et_date.setText(
                        et_date.text.toString() + " " + String.format(
                            "%2d:%2d",
                            hourOfDay,
                            minute
                        ).replace(' ', '0')
                    )
                    calendarSet!![Calendar.HOUR_OF_DAY] = hourOfDay
                    calendarSet!![Calendar.MINUTE] = minute
                }, 0, 0, true)
            //if the user has not selected a date, set the date to the current date
            if (calendarSet == null) {
                calendarSet = GregorianCalendar()
            }
            timePickerDialog!!.show()
            datePickerDialog!!.show()
        }

        /*
         * Custom Spinner
         */
        val checkedGame = BooleanArray(Arrays.asList(*resources.getStringArray(R.array.games)).size)
        val tv_games = findViewById<TextView>(R.id.tv_games)
        val selectedGames = ArrayList<Int>()
        tv_games.setOnClickListener { v: View? ->
            val builder = AlertDialog.Builder(this@CreateLan)
            builder.setTitle("Choose games")
            builder.setCancelable(false)
            builder.setMultiChoiceItems(
                R.array.games,
                checkedGame
            ) { dialogInterface: DialogInterface?, i: Int, isChecked: Boolean ->
                if (isChecked) {
                    selectedGames.add(i)
                    Collections.sort(selectedGames)
                } else {
                    selectedGames.remove(Integer.valueOf(i))
                }
            }
            builder.setPositiveButton("OK") { dialog: DialogInterface?, i: Int ->
                val stringbuilder = StringBuilder()
                for (j in selectedGames.indices) {
                    stringbuilder.append(resources.getStringArray(R.array.games)[selectedGames[j]])
                    if (j != selectedGames.size - 1) {
                        if (j % 2 == 0) {
                            stringbuilder.append(",\n")
                        } else {
                            stringbuilder.append(", ")
                        }
                    }
                }
                //set text on text view
                tv_games.text = stringbuilder.toString()
            }
            builder.setNegativeButton("Cancel") { dialog: DialogInterface, i: Int -> dialog.dismiss() }
            builder.setNeutralButton("Clear all") { dialog: DialogInterface?, i: Int ->
                for (j in checkedGame.indices) {
                    checkedGame[j] = false
                    selectedGames.clear()
                    tv_games.text = ""
                }
            }
            builder.show()

            val locationSwitch = findViewById<SwitchCompat>(R.id.locationSwitch)
            if(locationSwitch.isChecked){
                checkLocationPermission()
            }
        }

        /*
         * Create a new LAN
         */
        findViewById<View>(R.id.btn_create).setOnClickListener(object : View.OnClickListener {
            //initialize variables
            val et_name = findViewById<EditText>(R.id.et_name)
            val et_plz = findViewById<EditText>(R.id.et_plz)
            val et_city = findViewById<EditText>(R.id.et_city)
            val et_maxPlayers = findViewById<EditText>(R.id.et_maxPlayers)
            val et_description = findViewById<EditText>(R.id.et_description)
            val tv_error_games = findViewById<TextView>(R.id.tv_error_game)

            //validate inputs
            override fun onClick(v: View) {
                if (et_name.text.toString().isEmpty()) {
                    et_name.error = "Please enter a name!"
                } else if (et_plz.text.toString().isEmpty()) {
                    et_plz.error = "Please enter a postal code!"
                } else if (et_city.text.toString().isEmpty()) {
                    et_city.error = "Please enter a city!"
                } else if (et_maxPlayers.text.toString().isEmpty()) {
                    et_maxPlayers.error = "Please enter a maximum number of players!"
                } else if (et_date.text.toString().length != 16) {
                    et_date.error = "Please select a date and time!"
                } else if (tv_games.text.toString().isEmpty()) {
                    tv_error_games.error = "Please select a game!"
                } else {
                    val builder = AlertDialog.Builder(this@CreateLan)
                    builder.setTitle("Create LAN")
                    builder.setMessage("Do you want to create this LAN?")
                    builder.setPositiveButton("Create") { dialog: DialogInterface?, which: Int ->
                        MockApiService.createLanParty(LanParty(
                            et_name.text.toString(),
                            et_plz.text.toString(),
                            et_city.text.toString(),
                            calendarSet,
                            if (et_maxPlayers.text.toString()
                                    .isEmpty()
                            ) 0 else et_maxPlayers.text.toString().toInt(),
                            HashSet(
                                Arrays.asList(
                                    *tv_games.text.toString().split(", ".toRegex())
                                        .dropLastWhile { it.isEmpty() }
                                        .toTypedArray())),
                            et_description.text.toString(),
                            MockApiService.currentUser))
                        //make a Toast if creation was successful and go back to MainActivity
                        val intent = Intent(this@CreateLan, MainActivity::class.java)
                        Toast.makeText(this@CreateLan, "LAN created", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }
                    builder.setNegativeButton("No") { dialog: DialogInterface, which: Int -> dialog.dismiss() }
                    builder.show()
                }
            }
        })
    }

    /*
     * Action Bar settings (back button)
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    /*
     * Get the current location of the user
     */
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission is already granted, proceed with accessing the location
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Location is retrieved successfully
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    // Speichere die GPS-Daten oder füge sie zur LAN-Party hinzu
                } else {
                    // Location is null, handle the case when location is not available
                }
            }
            .addOnFailureListener { exception: Exception ->
                // Failed to retrieve location, handle the error
            }
    }

}