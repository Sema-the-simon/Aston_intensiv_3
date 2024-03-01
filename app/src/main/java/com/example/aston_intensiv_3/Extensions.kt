package com.example.aston_intensiv_3

import com.example.aston_intensiv_3.model.Contact
import kotlin.random.Random

val listOfNames = listOf("Daniil", "Sasha", "Jenya", "Maxim", "Jonathon")
val listOfSurnames = listOf("Franchesko", "Jostar", "Kenya", "Svaytopolc", "Beluga")
fun getRandomPhoneNumber() = "+ ${Random.nextLong(7_900_000_0000, 7_999_999_9999)}"

fun getContacts() =
    List(100) { Contact(it, listOfNames.random(), listOfSurnames.random(), getRandomPhoneNumber()) }
