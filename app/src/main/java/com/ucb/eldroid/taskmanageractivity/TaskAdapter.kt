package com.ucb.eldroid.taskmanageractivity

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val taskList: ArrayList<Pair<String, String>>, private val context: Context) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskText: TextView = itemView.findViewById(R.id.taskText)
        val categoryText: TextView = itemView.findViewById(R.id.category)

        init {
            itemView.setOnClickListener {
                val task = taskList[adapterPosition]
                Toast.makeText(itemView.context, "You selected this task:  ${task.first}", Toast.LENGTH_SHORT).show()
            }

            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    AlertDialog.Builder(context)
                        .setTitle("Delete Task")
                        .setMessage("Deleting Task?")
                        .setPositiveButton("Delete") { _, _ ->
                            taskList.removeAt(position)
                            notifyItemRemoved(position)
                            Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show()
                        }
                        .setNegativeButton("Cancel", null)
                        .show()
                }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_list_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val taskPair = taskList[position]
        holder.taskText.text = taskPair.first
        holder.categoryText.text = taskPair.second
    }

    override fun getItemCount(): Int = taskList.size
}