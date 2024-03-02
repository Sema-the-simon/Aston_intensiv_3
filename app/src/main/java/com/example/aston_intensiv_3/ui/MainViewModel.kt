package com.example.aston_intensiv_3.ui

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.aston_intensiv_3.R
import com.example.aston_intensiv_3.getContacts
import com.example.aston_intensiv_3.getRandomId
import com.example.aston_intensiv_3.model.Contact
import com.example.aston_intensiv_3.put
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

const val INDEX_OF_NEW_ELEMENT = 0

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    fun changeContact(name: String, surname: String, phoneNumber: String) {
        val isAddNewContact = uiState.value.stateType == StateType.ADD
        val contactId =
            if (isAddNewContact) getRandomId() else uiState.value.editContactId
        val newContact = Contact(contactId, name, surname, phoneNumber)
        val newList = _uiState.value.contactList.toMutableList().also { list ->
            if (isAddNewContact)
                list.add(INDEX_OF_NEW_ELEMENT, newContact)
            else
                list.put(newContact)
        }

        _uiState.update { uiState ->
            uiState.copy(
                contactList = newList,
                stateType = StateType.IDLE
            )
        }
    }

    fun changeStateTo(stateType: StateType, id: Int = -1) {
        _uiState.update {
            it.copy(
                stateType = stateType,
                editContactId = id
            )
        }
    }

    fun toggleDeleteState() {
        if (uiState.value.stateType != StateType.DELETE)
            _uiState.update {
                it.copy(
                    stateType = StateType.DELETE,
                    isDeleteStateButtonEnable = false,
                    addButtonVisibility = View.GONE,
                    deleteButtonsVisibility = View.VISIBLE,
                    titleTextResId = R.string.title_delete
                )
            }
        else
            _uiState.update {
                it.copy(
                    stateType = StateType.IDLE,
                    isDeleteStateButtonEnable = true,
                    addButtonVisibility = View.VISIBLE,
                    deleteButtonsVisibility = View.GONE,
                    titleTextResId = R.string.main_title
                )
            }
    }

    fun deleteSelected() {
        val newList = mutableListOf<Contact>()
        uiState.value.contactList.forEach { contact ->
            if (!contact.isSelected)
                newList.add(contact)
        }
        toggleDeleteState()
        _uiState.update {
            it.copy(
                contactList = newList
            )
        }
    }
}

data class MainUiState(
    val contactList: List<Contact> = getContacts(),
    val editContactId: Int = -1,
    val stateType: StateType = StateType.IDLE,
    val isDeleteStateButtonEnable: Boolean = true,
    val addButtonVisibility: Int = View.VISIBLE,
    val deleteButtonsVisibility: Int = View.GONE,
    val titleTextResId: Int = R.string.main_title
)

enum class StateType { IDLE, ADD, EDIT, DELETE }