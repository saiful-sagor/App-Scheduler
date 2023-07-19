package com.example.scheduler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScheduleAdapter(private val scheduleList: MutableList<Schedule>) :
    RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.schedule_item, parent, false)
        return ScheduleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val currentSchedule = scheduleList[position]
        holder.tvAppName.text = currentSchedule.appName
        holder.tvScheduledTime.text = currentSchedule.scheduledTime

        holder.btnDelete.setOnClickListener {
            scheduleList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

    inner class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAppName: TextView = itemView.findViewById(R.id.tvAppName)
        val tvScheduledTime: TextView = itemView.findViewById(R.id.tvScheduledTime)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }
}
