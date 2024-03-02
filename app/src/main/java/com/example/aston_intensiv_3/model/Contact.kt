package com.example.aston_intensiv_3.model

data class Contact(
    val id: Int,
    val name: String,
    val surname: String,
    val phoneNumber: String,
    var isSelected: Boolean = false
)
