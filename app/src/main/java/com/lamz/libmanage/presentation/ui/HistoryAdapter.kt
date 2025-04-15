package com.lamz.libmanage.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.lamz.libmanage.data.entity.HistoryEntity
import com.lamz.libmanage.databinding.ItemHistoryBinding
import com.lamz.libmanage.presentation.ui.history.HistoryViewModel
import kotlinx.coroutines.launch

class HistoryAdapter(private val historyList: List<HistoryEntity>, private val viewModel: HistoryViewModel) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = historyList[position]

        viewModel.viewModelScope.launch {
            val book = viewModel.getBookById(history.bookId)
            if (book != null) {
                holder.binding.tvNameBook.text = book.title
            }
        }

        holder.binding.tvDate.text = history.date
        holder.binding.tvStatus.text = history.status
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    class HistoryViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)
}

