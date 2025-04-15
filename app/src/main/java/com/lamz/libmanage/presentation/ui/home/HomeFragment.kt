package com.lamz.libmanage.presentation.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lamz.libmanage.R
import com.lamz.libmanage.data.entity.BookEntity
import com.lamz.libmanage.databinding.FragmentHomeBinding
import com.lamz.libmanage.presentation.ui.BookAdapter
import com.lamz.libmanage.utils.Helper.getTodayDate
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupObserver()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObserver() {
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            val isAdmin = user.role == "admin"
            observeBooks(isAdmin)
        }
    }

    private fun observeBooks(isAdmin: Boolean) {
        viewModel.getBooks().observe(viewLifecycleOwner) { books ->
            binding.rvBooks.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = BookAdapter(books) { selectedBook ->
                    showBookDialog(selectedBook, isAdmin)
                }
            }
        }
    }

    private fun showBookDialog(book: BookEntity, isAdmin: Boolean) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Detail Buku")
            .setMessage(bookDetailsMessage(book))
            .apply {
                if (isAdmin) {
                    setPositiveButton("Edit") { _, _ -> showEditDialog(book) }
                    setNegativeButton("Hapus") { _, _ -> confirmDeleteBook(book) }
                } else {
                    setPositiveButton("Pinjam") { _, _ -> handleBorrow(book) }
                    setNegativeButton("Pengembalian") { _, _ -> handleReturn(book) }
                }
                setNeutralButton("Tutup", null)
            }
            .create()

        dialog.show()
    }

    private fun bookDetailsMessage(book: BookEntity): String = """
        Judul: ${book.title}
        Penulis: ${book.author}
        Tahun: ${book.year}
        Stok: ${book.stock}
    """.trimIndent()

    private fun confirmDeleteBook(book: BookEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Hapus")
            .setMessage("Yakin ingin menghapus buku ini?")
            .setPositiveButton("Ya") { _, _ -> viewModel.deleteBook(book) }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun showEditDialog(book: BookEntity) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_book, null)

        val edtTitle = dialogView.findViewById<EditText>(R.id.edtTitle).apply {
            setText(book.title)
        }
        val edtAuthor = dialogView.findViewById<EditText>(R.id.edtAuthor).apply {
            setText(book.author)
        }
        val edtYear = dialogView.findViewById<EditText>(R.id.edtYear).apply {
            setText(book.year.toString())
        }
        val edtStock = dialogView.findViewById<EditText>(R.id.edtStock).apply {
            setText(book.stock.toString())
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Buku")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val updatedBook = book.copy(
                    title = edtTitle.text.toString(),
                    author = edtAuthor.text.toString(),
                    year = edtYear.text.toString().toIntOrNull() ?: 0,
                    stock = edtStock.text.toString().toIntOrNull() ?: 0
                )
                viewModel.updateBook(updatedBook)
            }
            .setNegativeButton("Batal", null)
            .show()
    }


    private fun handleBorrow(book: BookEntity) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_input_date, null)
        val edtDate = dialogView.findViewById<EditText>(R.id.edtBorrowDate)

        val calendar = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val selectedDate = String.format("%04d-%02d-%02d", year, month + 1, day)
            edtDate.setText(selectedDate)
        }

        edtDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        edtDate.setText(getTodayDate())

        AlertDialog.Builder(requireContext())
            .setTitle("Tanggal Peminjaman")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val date = edtDate.text.toString()
                if (book.stock > 0) {
                    viewModel.borrowBook(book, date)
                } else {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Stok Habis")
                        .setMessage("Maaf, stok buku sudah habis.")
                        .setPositiveButton("OK", null)
                        .show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }



    private fun handleReturn(book: BookEntity) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_input_date, null)
        val edtDate = dialogView.findViewById<EditText>(R.id.edtBorrowDate)

        val calendar = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val selectedDate = String.format("%04d-%02d-%02d", year, month + 1, day)
            edtDate.setText(selectedDate)
        }

        edtDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        edtDate.setText(getTodayDate())

        AlertDialog.Builder(requireContext())
            .setTitle("Tanggal Pengembalian")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val date = edtDate.text.toString()
                viewModel.returnBook(book, date)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

}