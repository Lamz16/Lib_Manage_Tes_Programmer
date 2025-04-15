package com.lamz.libmanage.presentation.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lamz.libmanage.databinding.FragmentHistoryBinding
import com.lamz.libmanage.presentation.ui.HistoryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by viewModel()

    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        observeHistoryData()

        return root
    }

    private fun setupRecyclerView() {
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        historyAdapter = HistoryAdapter(emptyList(), viewModel)
        binding.rvHistory.adapter = historyAdapter
    }

    private fun observeHistoryData() {
        viewModel.getHistory().observe(viewLifecycleOwner) { historyList ->
            historyAdapter = HistoryAdapter(historyList, viewModel)
            binding.rvHistory.adapter = historyAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
