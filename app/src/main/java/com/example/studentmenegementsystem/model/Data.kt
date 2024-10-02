package com.example.studentmenegementsystem.model

import com.google.firebase.Timestamp

data class Data(
    var id: String? = null,
    var studentId: String? = null,
    var name: String? = null,
    var email: String? = null,
    var subject: String? = null,
    var birthDate: String? = null,
    val timestamp: Timestamp? = null
)
