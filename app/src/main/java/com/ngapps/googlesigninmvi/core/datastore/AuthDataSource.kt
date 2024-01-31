package com.ngapps.googlesigninmvi.core.datastore

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import com.ngapps.googlesigninmvi.core.model.AuthDto
import java.io.IOException
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val encryptedSharedPreferences: EncryptedSharedPreferences,
) {
    fun saveAuth(auth: AuthDto): AuthDto {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                encryptedSharedPreferences.edit()
                    .putString(TOKEN_KEY, auth.token)
                    .putString(GIVEN_NAME_KEY, auth.givenName)
                    .putString(FAMILY_NAME_KEY, auth.familyName)
                    .putString(PHOTO_URL_KEY, auth.photoUrl)
                    .apply()
            } else {
                sharedPreferences.edit()
                    .putString(TOKEN_KEY, auth.token)
                    .putString(GIVEN_NAME_KEY, auth.givenName)
                    .putString(FAMILY_NAME_KEY, auth.familyName)
                    .putString(PHOTO_URL_KEY, auth.photoUrl)
                    .apply()
            }
            getAuth()
        } catch (ioException: IOException) {
            Log.e("AuthTokenDataSource", "Failed to update tokens", ioException)
            AuthDto.init()
        }
    }

    fun getAuth(): AuthDto {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            encryptedSharedPreferences.let {
                AuthDto(
                    token = it.getString(TOKEN_KEY, null) ?: "",
                    givenName = it.getString(GIVEN_NAME_KEY, null) ?: "",
                    familyName = it.getString(FAMILY_NAME_KEY, null) ?: "",
                    photoUrl = it.getString(PHOTO_URL_KEY, null) ?: ""
                )
            }
        } else {
            sharedPreferences.let {
                AuthDto(
                    token = it.getString(TOKEN_KEY, null) ?: "",
                    givenName = it.getString(GIVEN_NAME_KEY, null) ?: "",
                    familyName = it.getString(FAMILY_NAME_KEY, null) ?: "",
                    photoUrl = it.getString(PHOTO_URL_KEY, null) ?: ""
                )
            }
        }
    }

    fun deleteToken() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            encryptedSharedPreferences.edit().clear().apply()
        } else {
            sharedPreferences.edit().clear().clear().apply()
        }
    }

    companion object {
        private const val TOKEN_KEY = "token"
        private const val GIVEN_NAME_KEY = "given_name"
        private const val FAMILY_NAME_KEY = "family_name"
        private const val PHOTO_URL_KEY = "photo_url"
    }
}