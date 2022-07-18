package com.app.garant.data.request.auth

import android.content.Intent
import android.net.Uri
import java.io.File

data class DocumentRequest(
    val file: File,
    val type: String
)
