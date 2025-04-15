package com.lamz.libmanage.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lamz.libmanage.R
import com.lamz.libmanage.data.entity.UserEntity
import com.lamz.libmanage.data.pref.UserModel
import com.lamz.libmanage.databinding.ActivityLoginBinding
import com.lamz.libmanage.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel : LoginViewModel by viewModel()
    private val listUser = listOf(
        UserEntity(id = 1, username = "admin1", password = "admin123", role = "admin"),
        UserEntity(id = 2, username = "petugas1", password = "petugas123", role = "petugas")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding){
            btnLogin.setOnClickListener {
                val username = usernameEdt.text.toString().trim()
                val password = passwordEdt.text.toString().trim()

                if (username.isEmpty() || password.isEmpty()) {
                    showAlert("Peringatan", "Username dan password tidak boleh kosong.")
                    return@setOnClickListener
                }

                progressBar.visibility = View.VISIBLE
                btnLogin.isEnabled = false

                Handler(Looper.getMainLooper()).postDelayed({

                    val user = listUser.find {
                        it.username == username && it.password == password
                    }

                    progressBar.visibility = View.GONE
                    btnLogin.isEnabled = true

                    if (user != null) {
                        viewModel.saveSession(
                            UserModel(
                                user = user.username,
                                role = user.role
                            )
                        )
                        AlertDialog.Builder(this@LoginActivity).apply {
                            setTitle("Yeah!")
                            setMessage("Anda berhasil login. Selamat Datang")
                            setPositiveButton("Lanjut") { _, _ ->
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    } else {
                        showAlert("Login Gagal", "Username atau password salah.")
                    }
                }, 2000)
            }

        }
    }

    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}

