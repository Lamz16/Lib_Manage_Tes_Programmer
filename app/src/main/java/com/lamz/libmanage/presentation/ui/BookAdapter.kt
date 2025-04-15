package com.lamz.libmanage.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lamz.libmanage.data.entity.BookEntity
import com.lamz.libmanage.databinding.ItemBookBinding

class BookAdapter(
    private val list: List<BookEntity>,
    private val onItemClick: (BookEntity) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = list[position]
        with(holder.binding) {
            tvTitle.text = book.title
            tvStock.text = "Stock: ${book.stock}"
            root.setOnClickListener {
                onItemClick(book)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}

