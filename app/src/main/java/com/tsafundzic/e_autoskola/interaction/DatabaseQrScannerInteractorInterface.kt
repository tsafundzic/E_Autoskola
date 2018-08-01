package com.tsafundzic.e_autoskola.interaction

import com.google.firebase.database.DatabaseReference

interface DatabaseQrScannerInteractorInterface {

    fun getDatabaseRef(): DatabaseReference

    fun getCandidateNAme(candidateId: String)
}