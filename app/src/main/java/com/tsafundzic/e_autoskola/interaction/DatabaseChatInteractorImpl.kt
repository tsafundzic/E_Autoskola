package com.tsafundzic.e_autoskola.interaction

import com.google.firebase.database.*
import com.tsafundzic.e_autoskola.common.constants.*
import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.models.Instructor
import com.tsafundzic.e_autoskola.presentation.ChatInterface

class DatabaseChatInteractorImpl(private var databaseListener: ChatInterface.onDatabaseListener) : DatabaseChatInteractorInterface {
    override fun getAssignedCandidates(instructor: Instructor) {
        val assignedCandidates = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val candidates = ArrayList<Candidate>()

                for (userSnapshot in dataSnapshot.children) {
                    if (userSnapshot.child(ROLE).value.toString() == CANDIDATE && userSnapshot.child(SELECTEDINSTRUCTOR).value.toString() == instructor.role) {
                        val candidate = Candidate(userSnapshot.child(ADDRESS).value.toString(),
                                userSnapshot.child(BIRTHDATE).value.toString(),
                                //candidate ID saved to candidate CATEGORY
                                userSnapshot.key.toString(),
                                userSnapshot.child(EMAIL).value.toString(),
                                userSnapshot.child(NAME).value.toString(),
                                userSnapshot.child(OIB).value.toString(),
                                userSnapshot.child(PAYMENT).value.toString(),
                                userSnapshot.child(PHONE).value.toString(),
                                userSnapshot.child(ROLE).value.toString(),
                                userSnapshot.child(SELECTEDSCHOOL).value.toString(),
                                userSnapshot.child(SELECTEDINSTRUCTOR).value.toString(),
                                userSnapshot.child(PASSEDDRIVETEST).value.toString(),
                                userSnapshot.child(PASSEDFIRSTAID).value.toString(),
                                userSnapshot.child(PASSEDTRAFFICREGULATIONS).value.toString()
                        )
                        candidates.add(candidate)
                    }
                }
                candidates.sortBy { it.name }
                databaseListener.returnAssignedCandidates(candidates)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        getDatabaseRef().child(USERS).addListenerForSingleValueEvent(assignedCandidates)
    }

    override fun getCurrentCandidateData(userUid: String) {
        val candidateData = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val candidate = Candidate(dataSnapshot.child(ADDRESS).value.toString(),
                        dataSnapshot.child(BIRTHDATE).value.toString(),
                        //candidate ID saved to candidate CATEGORY
                        dataSnapshot.key.toString(),
                        dataSnapshot.child(EMAIL).value.toString(),
                        dataSnapshot.child(NAME).value.toString(),
                        dataSnapshot.child(OIB).value.toString(),
                        dataSnapshot.child(PAYMENT).value.toString(),
                        dataSnapshot.child(PHONE).value.toString(),
                        dataSnapshot.child(ROLE).value.toString(),
                        dataSnapshot.child(SELECTEDSCHOOL).value.toString(),
                        dataSnapshot.child(SELECTEDINSTRUCTOR).value.toString(),
                        dataSnapshot.child(PASSEDDRIVETEST).value.toString(),
                        dataSnapshot.child(PASSEDFIRSTAID).value.toString(),
                        dataSnapshot.child(PASSEDTRAFFICREGULATIONS).value.toString()
                )
                databaseListener.returnCurrentCandidateData(candidate)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        myRef.child(USERS).child(userUid).addListenerForSingleValueEvent(candidateData)
    }

    override fun getCurrentInstructorData(userUid: String) {
        val instructorData = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val instructor = Instructor(dataSnapshot.child(ADDRESS).value.toString(),
                        dataSnapshot.child(BIRTHDATE).value.toString(),
                        //instructor ID saved to instructor role
                        dataSnapshot.key.toString(),
                        dataSnapshot.child(PHONE).value.toString(),
                        dataSnapshot.child(NAME).value.toString(),
                        dataSnapshot.child(OIB).value.toString(),
                        dataSnapshot.child(SELECTEDSCHOOL).value.toString(),
                        dataSnapshot.child(EMAIL).value.toString()
                )
                databaseListener.returnCurrentInstructorData(instructor)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        myRef.child(USERS).child(userUid).addListenerForSingleValueEvent(instructorData)
    }

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    override fun getDatabaseRef(): DatabaseReference {
        return myRef
    }

    override fun getSelectedInstructor(candidate: Candidate) {
        val selectedInstructor = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val instructors = ArrayList<Instructor>()

                for (userSnapshot in dataSnapshot.children) {
                    if (userSnapshot.child(ROLE).value.toString() == INSTRUCTOR && userSnapshot.key == candidate.selectedInstructor) {
                        val instructor = Instructor(userSnapshot.child(ADDRESS).value.toString(),
                                userSnapshot.child(BIRTHDATE).value.toString(),
                                //instructor ID saved to instructor role
                                userSnapshot.key.toString(),
                                userSnapshot.child(PHONE).value.toString(),
                                userSnapshot.child(NAME).value.toString(),
                                userSnapshot.child(OIB).value.toString(),
                                userSnapshot.child(SELECTEDSCHOOL).value.toString(),
                                userSnapshot.child(EMAIL).value.toString()
                        )
                        instructors.add(instructor)
                    }
                }
                databaseListener.returnInstructors(instructors)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        getDatabaseRef().child(USERS).addListenerForSingleValueEvent(selectedInstructor)
    }
}