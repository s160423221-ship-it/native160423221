package com.example.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitRepository

class HabitViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = HabitRepository(application)
    private val _habits = MutableLiveData<MutableList<Habit>>()
    val habits: LiveData<MutableList<Habit>> get() = _habits

    init {
        _habits.value = repository.loadHabits()
    }

    fun addHabit(habit: Habit) {
        val currentList = _habits.value ?: mutableListOf()
        habit.id = if (currentList.isEmpty()) 1 else (currentList.maxOf { it.id } + 1)
        currentList.add(habit)
        _habits.value = currentList
        repository.saveHabits(currentList)
    }

    fun incrementProgress(habitId: Int) {
        val list = _habits.value ?: return
        val habit = list.find { it.id == habitId } ?: return
        if (habit.progress < habit.goal) {
            habit.progress++
            repository.saveHabits(list)
            _habits.value = list
        }
    }

    fun decrementProgress(habitId: Int) {
        val list = _habits.value ?: return
        val habit = list.find { it.id == habitId } ?: return
        if (habit.progress > 0) {
            habit.progress--
            repository.saveHabits(list)
            _habits.value = list
        }
    }
}