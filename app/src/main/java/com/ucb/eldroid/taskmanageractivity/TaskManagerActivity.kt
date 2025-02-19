package com.ucb.eldroid.taskmanageractivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TaskManagerActivity : AppCompatActivity() {
    private lateinit var taskInput: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var addButton: Button
    private lateinit var clearButton: Button
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private var taskList = ArrayList<Pair<String, String>>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_manager)

        taskInput = findViewById(R.id.taskInput)
        categorySpinner = findViewById(R.id.categorySpinner)
        addButton = findViewById(R.id.addButton)
        clearButton = findViewById(R.id.clearButton)
        taskRecyclerView = findViewById(R.id.taskRecyclerView)

        val categories = resources.getStringArray(R.array.task_categories)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = spinnerAdapter

        sharedPreferences = getSharedPreferences("TaskPrefs", Context.MODE_PRIVATE)
        fetchTasks()

        taskAdapter = TaskAdapter(taskList, this)
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        taskRecyclerView.adapter = taskAdapter

        addButton.setOnClickListener { addTask() }
        clearButton.setOnClickListener { emptyList() }
    }

    private fun addTask() {
        val task = taskInput.text.toString().trim()
        val category = categorySpinner.selectedItem.toString()
        if (task.isNotEmpty()) {
            taskList.add(Pair(task, category))
            taskAdapter.notifyItemInserted(taskList.size - 1)
            saveTasks()
            taskInput.text.clear()
        } else {
            Toast.makeText(this, "Please input a task!", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun emptyList() {
        taskList.clear()
        taskAdapter.notifyDataSetChanged()
        saveTasks()
    }

    private fun saveTasks() {
        val editor = sharedPreferences.edit()
        val taskSet = taskList.map { "${it.first} - ${it.second}" }.toSet()
        editor.putStringSet("tasks", taskSet)
        editor.apply()
    }

    private fun fetchTasks() {
        val set = sharedPreferences.getStringSet("tasks", HashSet())
        if (set != null) {
            for (item in set) {
                val parts = item.split(" - ")
                if (parts.size == 2) {
                    taskList.add(Pair(parts[0], parts[1]))
                }
            }
        }
    }
}