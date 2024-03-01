package com.example.aston_intensiv_3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.aston_intensiv_2.ui.MainUiState
import com.example.aston_intensiv_2.ui.MainViewModel
import com.example.aston_intensiv_3.databinding.ActivityMainBinding
import com.example.aston_intensiv_3.recycler.ContactsAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateUi(uiState)
                }
            }
        }
    }

    private fun updateUi(state: MainUiState) {
        val adapter = ContactsAdapter { model ->
            Toast.makeText(this, "Hello its me ${model.name} ${model.surname}", Toast.LENGTH_LONG).show()
        }
        binding.contactRecycler.adapter = adapter
        adapter.submitList(state.contactList)
    }
}