package com.example.aston_intensiv_2.ui

import androidx.lifecycle.ViewModel
import com.example.aston_intensiv_3.getContacts
import com.example.aston_intensiv_3.model.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

}

data class MainUiState(
    val contactList: List<Contact> = getContacts(),
)
