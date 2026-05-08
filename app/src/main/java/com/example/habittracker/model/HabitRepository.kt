package com.example.habittracker.model

import android.content.Context
import com.example.habittracker.util.FileHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HabitRepository(private val context: Context) {

    private val fileHelper = FileHelper(context)
    private val gson = Gson()

    fun loadHabits(): MutableList<Habit> {
        val json = fileHelper.readFromFile()
        return if (json.isNotBlank()) {
            val type = object : TypeToken<MutableList<Habit>>() {}.type
            gson.fromJson(json, type)
        } else {
            mutableListOf()
        }
    }

    fun saveHabits(habits: MutableList<Habit>) {
        val json = gson.toJson(habits)
        fileHelper.writeToFile(json)
    }
}