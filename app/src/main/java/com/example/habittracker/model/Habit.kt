package com.example.habittracker.model

data class Habit(
    var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var goal: Int = 0,
    var progress: Int = 0,
    var unit: String = "",
    var iconResId: Int = 0
)
