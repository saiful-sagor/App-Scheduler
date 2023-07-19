package com.example.scheduler

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var scheduleAdapter: ScheduleAdapter
    private val scheduleList = mutableListOf<Schedule>()

    companion object {
        const val ADD_SCHEDULE_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddSchedule = findViewById<Button>(R.id.btnAddSchedule)
        btnAddSchedule.setOnClickListener {
            val intent = Intent(this, AddScheduleActivity::class.java)
            startActivityForResult(intent, ADD_SCHEDULE_REQUEST_CODE)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        scheduleAdapter = ScheduleAdapter(scheduleList)
        recyclerView.adapter = scheduleAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_SCHEDULE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val schedule = data?.getParcelableExtra<Schedule>(AddScheduleActivity.EXTRA_SCHEDULE)
            schedule?.let {
                scheduleList.add(it)
                scheduleAdapter.notifyDataSetChanged()
            }
        }
    }
}
