package com.example.aston_intensiv_3.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.aston_intensiv_3.databinding.ActivityMainBinding
import com.example.aston_intensiv_3.ui.recycler.ContactsAdapter
import kotlinx.coroutines.launch

const val ADD_CONTACT_FRAGMENT_TAG = "ADD_CONTACT_FRAGMENT_TAG"
const val EDIT_CONTACT_FRAGMENT_TAG = "EDIT_CONTACT_FRAGMENT_TAG"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val adapter = ContactsAdapter() { model ->
        val bundle = Bundle().apply {
            putString(BUNDLE_KEY_NAME, model.name)
            putString(BUNDLE_KEY_SURNAME, model.surname)
            putString(BUNDLE_KEY_PHONE, model.phoneNumber)
        }
        supportFragmentManager.setFragmentResult(
            EDIT_CONTACT_RESULT_KEY, bundle
        )
        ChangeContactFragment().show(supportFragmentManager, EDIT_CONTACT_FRAGMENT_TAG)
        viewModel.changeStateTo(StateType.EDIT, model.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.contactRecycler.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateUi(uiState)
                    adapter.submitList(uiState.contactList)
                }
            }
        }

        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            fabAddContact.setOnClickListener {
                ChangeContactFragment().show(supportFragmentManager, ADD_CONTACT_FRAGMENT_TAG)
                viewModel.changeStateTo(StateType.ADD)
            }

            btnDeleteStart.setOnClickListener {
                viewModel.toggleDeleteState()
            }

            btnDeleteSave.setOnClickListener {
                viewModel.deleteSelected()
            }

            btnDeleteCancel.setOnClickListener {
                viewModel.toggleDeleteState()
            }
        }

        supportFragmentManager.setFragmentResultListener(
            ADD_CONTACT_RESULT_KEY,
            this
        ) { _, bundle ->
            val name = bundle.getString(BUNDLE_KEY_NAME).toString()
            val surname = bundle.getString(BUNDLE_KEY_SURNAME).toString()
            val phoneNumber = bundle.getString(BUNDLE_KEY_PHONE).toString()
            viewModel.changeContact(name, surname, phoneNumber)
        }
    }

    private fun updateUi(state: MainUiState) {
        adapter.setStateType(state.stateType)
        binding.apply {
            btnDeleteStart.isEnabled = state.isDeleteStateButtonEnable
            fabAddContact.visibility = state.addButtonVisibility
            deleteGroup.visibility = state.deleteButtonsVisibility

            tvTitle.text = getString(state.titleTextResId)
        }
    }
}