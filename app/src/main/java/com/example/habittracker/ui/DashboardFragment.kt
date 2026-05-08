package com.example.habittracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentDashboardBinding
import com.example.habittracker.model.Habit
import com.example.habittracker.ui.adapter.HabitAdapter
import com.example.habittracker.viewmodel.HabitViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HabitViewModel by activityViewModels()
    private lateinit var adapter: HabitAdapter
    private val habitsList: MutableList<Habit> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HabitAdapter(
            habitsList,
            onIncrement = { habit -> viewModel.incrementProgress(habit.id) },
            onDecrement = { habit -> viewModel.decrementProgress(habit.id) }
        )

        binding.recyclerViewHabits.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewHabits.adapter = adapter

        viewModel.habits.observe(viewLifecycleOwner) { list ->
            habitsList.clear()
            if (list != null) habitsList.addAll(list)
            adapter.notifyDataSetChanged()
        }

        binding.fabAddHabit.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_newHabitFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
