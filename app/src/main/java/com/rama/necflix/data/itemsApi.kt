package com.rama.necflix.data

data class requestToken(
    val expires_at: String,
    val request_token: String,
    val success: Boolean
)
data class sessionId(
    val session_id: String,
    val success: Boolean
)