package com.lamz.libmanage.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.lamz.libmanage.R
import com.lamz.libmanage.data.entity.BookEntity
import com.lamz.libmanage.databinding.ActivityMainBinding
import com.lamz.libmanage.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

            if (user.role == "petugas") {
                binding.appBarMain.fab.hide()
            } else {
                binding.appBarMain.fab.show()
            }
        }

        binding.appBarMain.fab.setOnClickListener {
          addBook()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_riwayat
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                viewModel.logout()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun addBook(){
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_book, null)
        val titleInput = dialogView.findViewById<EditText>(R.id.edtTitle)
        val authorInput = dialogView.findViewById<EditText>(R.id.edtAuthor)
        val yearInput = dialogView.findViewById<EditText>(R.id.edtYear)
        val stockInput = dialogView.findViewById<EditText>(R.id.edtStock)

        AlertDialog.Builder(this)
            .setTitle("Tambah Buku")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val title = titleInput.text.toString()
                val author = authorInput.text.toString()
                val year = yearInput.text.toString().toIntOrNull() ?: 0
                val stock = stockInput.text.toString().toIntOrNull() ?: 0

                val book = BookEntity(title = title, author = author, year = year, stock = stock)
                viewModel.insertBook(book)
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}