package com.ngapps.googlesigninmvi.core.model

data class AuthDto(
    val token: String,
    val givenName: String?,
    val familyName: String?,
    val photoUrl: String?,
) {
    companion object {
        fun init() = AuthDto(
            token = "",
            givenName = null,
            familyName = null,
            photoUrl = null
        )
    }
}