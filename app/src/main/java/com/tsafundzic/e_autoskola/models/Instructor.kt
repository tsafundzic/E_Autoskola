package com.tsafundzic.e_autoskola.models

data class Instructor(
        val address: String,
        val birthdate: String,
        val email: String,
        val name: String,
        val oib: Int,
        val phone: Int,
        val role: String,
        var selectedSchool: String
)