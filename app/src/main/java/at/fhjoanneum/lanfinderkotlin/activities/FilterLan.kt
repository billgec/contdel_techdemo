package at.fhjoanneum.lanfinderkotlin.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import at.fhjoanneum.lanfinderkotlin.R
import java.util.*

/**
 * This activity is used to create a new LAN.
 */
class FilterLan : AppCompatActivity() {
    //instances for date and time picker
    private var datePickerDialog: DatePickerDialog? = null
    private var timePickerDialog: TimePickerDialog? = null
    private var calendarSet: GregorianCalendar? = null

    @SuppressLint("SetTextI18n", "DefaultLocale", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        val btnFilter = findViewById<View>(R.id.btn_filter)

        /*
         * Action Bar settings (set logo to action bar and back button)
         */
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.filter_title)
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
                this@FilterLan,
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
                TimePickerDialog(this@FilterLan, { view: TimePicker?, hourOfDay: Int, minute: Int ->
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
            val builder = AlertDialog.Builder(this@FilterLan)
            builder.setTitle(getString(R.string.choose_games))
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
            builder.setPositiveButton(getString(R.string.ok)) { dialog: DialogInterface?, i: Int ->
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
            builder.setNegativeButton(getString(R.string.cancel)) { dialog: DialogInterface, i: Int -> dialog.dismiss() }
            builder.setNeutralButton(getString(R.string.clear_all)) { dialog: DialogInterface?, i: Int ->
                for (j in checkedGame.indices) {
                    checkedGame[j] = false
                    selectedGames.clear()
                    tv_games.text = ""
                }
            }
            builder.show()
        }

        /*
         * Create a new LAN
         */
        btnFilter.setOnClickListener(object : View.OnClickListener {
            //initialize variables
            val et_name = findViewById<EditText>(R.id.et_name)
            val et_plz = findViewById<EditText>(R.id.et_plz)
            val et_city = findViewById<EditText>(R.id.et_city)
            val et_maxPlayers = findViewById<EditText>(R.id.et_maxPlayers)
            val tv_error_games = findViewById<TextView>(R.id.tv_error_game)

            //validate inputs
            override fun onClick(v: View) {
                val intent = Intent(this@FilterLan, MainActivity::class.java)

                intent.putExtra("plz", et_plz.text.toString())
                intent.putExtra("city", et_city.text.toString())
                intent.putExtra("maxPlayers", et_maxPlayers.text.toString())
                intent.putExtra("errorGames", tv_error_games.text.toString())

                startActivity(intent)
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
}
