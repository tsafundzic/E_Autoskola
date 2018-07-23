package com.tsafundzic.e_autoskola.models

data class Candidate(
        val passedDriveTest: String,
        val passedFirstAid: String,
        val passedTrafficRegulations: String,
        val address: String,
        val birthDate: String,
        val category: String,
        val email: String,
        val name: String,
        val oib: String,
        val payment: String,
        val phone: String,
        val role: String,
        val selectedSchool: String,
        val selectedInstructor: String
)