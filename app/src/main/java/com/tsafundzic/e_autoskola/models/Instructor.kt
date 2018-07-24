package com.tsafundzic.e_autoskola.models

data class Instructor(
        val address: String,
        val birthdate: String,
        val role: String,
        val phone: String,
        val name: String,
        val oib: String,
        var selectedSchool: String,
        val email: String

)



