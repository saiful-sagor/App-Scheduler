package com.example.scheduler


import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class AddScheduleActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_SCHEDULE = "extra_schedule"
        const val APP_PICKER_REQUEST_CODE = 2
    }

    private lateinit var timePicker: TimePicker
    private var selectedAppName: String = ""
    private var selectedPackage: String = ""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_schedule)

        timePicker = findViewById(R.id.timePicker)

        val btnSelectApp = findViewById<Button>(R.id.btnSelectApp)
        btnSelectApp.setOnClickListener {
            launchAppPicker()
        }

        val btnOk = findViewById<Button>(R.id.btnOk)
        btnOk.setOnClickListener {
            val selectedTime = "${timePicker.hour}:${timePicker.minute}"
            val schedule = Schedule(selectedAppName, selectedTime)

            val intent = packageManager.getLaunchIntentForPackage(selectedPackage)
            val pendingIntent = PendingIntent.getActivity(
                this@AddScheduleActivity,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            calendar.set(Calendar.MINUTE, timePicker.minute)
            calendar.set(Calendar.SECOND, 0)

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )

            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_SCHEDULE, schedule)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun launchAppPicker() {
        val packageManager = packageManager
        val apps = packageManager.getInstalledApplications(0)

        val appNames = mutableListOf<String>()
        val appPackageNames = mutableListOf<String>()

        for (app in apps) {
            appNames.add(packageManager.getApplicationLabel(app).toString())
            appPackageNames.add(app.packageName)
        }

        val items = appNames.toTypedArray()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select App")
        builder.setItems(items) { dialog, which ->
            selectedPackage = appPackageNames[which]
            selectedAppName = appNames[which]

            // Update the selected app information in your UI
            // For example:
            // updateUI(selectedAppName)
        }

        val dialog = builder.create()
        dialog.show()
    }
}





