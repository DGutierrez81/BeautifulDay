package com.project.beautifulday.Firebase

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class StorageService@Inject constructor(private val storage: FirebaseStorage) {


    fun uploadBasicImage(uri: Uri) {
        val reference = storage.reference.child(uri.lastPathSegment.orEmpty())
        reference.putFile(uri)
    }

    suspend fun uploadAndDownloadImage(uri: Uri): Uri {
        return suspendCancellableCoroutine<Uri> { cancellableContinuation ->
            val reference: StorageReference =
                storage.reference.child("picture/${uri.lastPathSegment}")
            reference.putFile(uri).addOnSuccessListener {
                downloadImage(it, cancellableContinuation)
            }.addOnFailureListener {
                cancellableContinuation.resumeWithException(it)
            }
        }
    }


    private fun downloadImage(
        uploadTask: UploadTask.TaskSnapshot, cancellableContinuation: CancellableContinuation<Uri>
    ) {
        uploadTask.storage.downloadUrl
            .addOnSuccessListener { uri -> cancellableContinuation.resume(uri) }
            .addOnFailureListener { cancellableContinuation.resumeWithException(it) }
    }




    suspend fun removeImage(imageName: String): Boolean {
        return try {
            val reference: StorageReference = storage.reference.child(imageName)
            // Verificar si el objeto existe antes de intentar eliminarlo
            val metadata = reference.metadata.await()
            if (metadata != null) {
                reference.delete().await()
                true
            } else {
                // El objeto no existe
                false
            }
        } catch (e: Exception) {
            // Manejar cualquier otra excepción
            // Log.e("removeImage", "Error removing image: ${e.message}")
            false
        }
    }



    suspend fun readMetadata(referencia: String): String? {
        val storage = Firebase.storage
        val reference = storage.reference.child(referencia)

        try {
            val response: StorageMetadata = reference.metadata.await()
            val mimeType: String? = response.contentType
            return when {
                mimeType == "image/jpeg" -> "image/jpeg"
                mimeType == "video/mp4" -> "video/mp4"
                else -> "Tipo desconocido"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }





    suspend fun getAllImages(): List<Uri>{
        val reference: StorageReference = storage.reference.child("picture/")

        val result: List<Uri> = reference.listAll().await().items.map{it.downloadUrl.await()}
        return result
    }

    suspend fun uploadVideoAndGetDownloadUrl(uri: Uri): Uri {
        return suspendCancellableCoroutine { continuation ->
            val reference: StorageReference = storage.reference.child("videos/${uri.lastPathSegment}")
            reference.putFile(uri).addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { downloadUri ->
                    continuation.resume(downloadUri)
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
    }



}