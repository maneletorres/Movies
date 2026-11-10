package com.manishsputnikcorporation.movies.data.remote

import android.content.Context
import com.manishsputnikcorporation.movies.network.services.executor.ServiceError
import com.manishsputnikcorporation.movies.utils.Either
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.squareup.moshi.Moshi

internal object LocalJsonAssetReader {

    private val moshi = Moshi.Builder().build()

    fun readJsonAsset(context: Context, fileName: String): String =
        context.assets.open(fileName).bufferedReader().use { it.readText() }.sanitizeJson()

    fun resolveDetailAssetPath(assetRoot: String, id: Int): String = "$assetRoot/detail/$id.json"

    fun <T> readJsonAssetAsDto(
        context: Context,
        fileName: String,
        clazz: Class<T>,
        missingMessage: String,
    ): Either<T, ServiceError> {
        return try {
            val json = readJsonAsset(context, fileName)
            val dto = moshi.adapter(clazz).fromJson(json)
            if (dto != null) eitherSuccess(dto) else eitherFailure(ServiceError(missingMessage, 0, emptyMap()))
        } catch (e: Exception) {
            eitherFailure(ServiceError(e.message ?: "Unknown JSON parsing error", 0, emptyMap()))
        }
    }

    fun <T> readListAsset(
        context: Context,
        fileName: String,
        clazz: Class<T>,
        missingMessage: String,
    ): Either<T, ServiceError> = readJsonAssetAsDto(
        context = context,
        fileName = fileName,
        clazz = clazz,
        missingMessage = missingMessage,
    )

    fun <T> readDetailAsset(
        context: Context,
        assetRoot: String,
        id: Int,
        clazz: Class<T>,
        missingMessage: String,
    ): Either<T, ServiceError> = readJsonAssetAsDto(
        context = context,
        fileName = resolveDetailAssetPath(assetRoot, id),
        clazz = clazz,
        missingMessage = missingMessage,
    )

    private fun String.sanitizeJson(): String =
        trimStart('\uFEFF', '\u200B', '\u2060', '\u00A0')
}
