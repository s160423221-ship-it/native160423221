package com.example.habittracker.ui.adapter

import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.habittracker.R
import com.example.habittracker.model.Habit

/**
 * Adapter RecyclerView untuk menampilkan daftar habit.
 * Setiap card memuat icon, nama habit, deskripsi, status, progress, progress bar,
 * serta tombol + dan - seperti pada gambar requirement project.
 */
class HabitAdapter(
    private val habits: MutableList<Habit>,
    private val onIncrement: (Habit) -> Unit,
    private val onDecrement: (Habit) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    inner class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        private val progressValueTextView: TextView = itemView.findViewById(R.id.progressValueTextView)
        private val btnIncrement: TextView = itemView.findViewById(R.id.btnIncrement)
        private val btnDecrement: TextView = itemView.findViewById(R.id.btnDecrement)
        private val statusStripeView: View = itemView.findViewById(R.id.statusStripeView)

        fun bind(habit: Habit) {
            val context = itemView.context
            val isCompleted = habit.progress >= habit.goal

            iconImageView.setImageResource(habit.iconResId)
            nameTextView.text = habit.name
            descriptionTextView.text = habit.description
            progressBar.max = habit.goal
            progressBar.progress = habit.progress
            progressValueTextView.text = context.getString(
                R.string.card_progress_format,
                habit.progress,
                habit.goal,
                habit.unit
            )

            if (isCompleted) {
                statusTextView.text = context.getString(R.string.progress_status_completed)
                statusTextView.setBackgroundResource(R.drawable.bg_badge_completed)
                statusTextView.setTextColor(ContextCompat.getColor(context, R.color.badge_green_text))
                statusStripeView.setBackgroundColor(ContextCompat.getColor(context, R.color.progress_green))
                progressBar.progressTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(context, R.color.progress_green)
                )
            } else {
                statusTextView.text = context.getString(R.string.progress_status_in_progress)
                statusTextView.setBackgroundResource(R.drawable.bg_badge_in_progress)
                statusTextView.setTextColor(ContextCompat.getColor(context, R.color.text_secondary))
                statusStripeView.setBackgroundColor(ContextCompat.getColor(context, R.color.progress_purple))
                progressBar.progressTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(context, R.color.progress_purple)
                )
            }

            btnIncrement.isEnabled = habit.progress < habit.goal
            btnDecrement.isEnabled = habit.progress > 0
            btnIncrement.alpha = if (btnIncrement.isEnabled) 1f else 0.4f
            btnDecrement.alpha = if (btnDecrement.isEnabled) 1f else 0.4f

            btnIncrement.setOnClickListener { onIncrement(habit) }
            btnDecrement.setOnClickListener { onDecrement(habit) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_habit, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position])
    }

    override fun getItemCount(): Int = habits.size
}
