package com.example.habittracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentNewHabitBinding
import com.example.habittracker.model.Habit
import com.example.habittracker.viewmodel.HabitViewModel

class NewHabitFragment : Fragment() {

    private var _binding: FragmentNewHabitBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HabitViewModel by activityViewModels()
    private lateinit var iconResIds: List<Int>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        iconResIds = listOf(
            R.drawable.ic_habit1,
            R.drawable.ic_habit2,
            R.drawable.ic_habit3
        )

        val iconNames = listOf("Drink Water", "Fitness", "Read Books")
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            iconNames
        )
        binding.iconSpinner.adapter = spinnerAdapter

        binding.createButton.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val description = binding.descriptionEditText.text.toString().trim()
            val goal = binding.goalEditText.text.toString().trim().toIntOrNull()
            val unit = binding.unitEditText.text.toString().trim()
            val selectedIndex = binding.iconSpinner.selectedItemPosition
            val iconResId = iconResIds.getOrElse(selectedIndex) { R.drawable.ic_habit1 }

            if (name.isEmpty() || description.isEmpty() || goal == null || goal <= 0 || unit.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.invalid_input_message),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val habit = Habit(
                id = 0,
                name = name,
                description = description,
                goal = goal,
                progress = 0,
                unit = unit,
                iconResId = iconResId
            )
            viewModel.addHabit(habit)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
