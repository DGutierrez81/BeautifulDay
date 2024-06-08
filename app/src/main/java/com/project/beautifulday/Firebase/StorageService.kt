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

/**
 * Clase que proporciona servicios de almacenamiento utilizando Firebase Storage.
 *
 * @property storage Instancia de FirebaseStorage proporcionada por inyección de dependencias.
 */
class StorageService @Inject constructor(private val storage: FirebaseStorage) {

    /**
     * Sube una imagen al almacenamiento y devuelve su URI de descarga.
     *
     * @param uri URI de la imagen a subir.
     * @param email Dirección de correo electrónico asociada a la imagen.
     * @return URI de descarga de la imagen subida.
     */
    suspend fun uploadAndDownloadImage(uri: Uri, email: String): Uri {
        return suspendCancellableCoroutine { cancellableContinuation ->
            val reference: StorageReference =
                storage.reference.child("picture/$email/${uri.lastPathSegment}")
            reference.putFile(uri).addOnSuccessListener {
                downloadImage(it, cancellableContinuation)
            }.addOnFailureListener {
                cancellableContinuation.resumeWithException(it)
            }
        }
    }

    /**
     * Descarga la imagen del almacenamiento y resuelve el resultado en un URI.
     *
     * @param uploadTask Tarea de carga de la imagen.
     * @param cancellableContinuation Continuación que se resolverá con el URI de descarga de la imagen.
     */
    private fun downloadImage(
        uploadTask: UploadTask.TaskSnapshot, cancellableContinuation: CancellableContinuation<Uri>
    ) {
        uploadTask.storage.downloadUrl
            .addOnSuccessListener { uri -> cancellableContinuation.resume(uri) }
            .addOnFailureListener { cancellableContinuation.resumeWithException(it) }
    }

    /**
     * Elimina una imagen del almacenamiento.
     *
     * @param imageName Nombre de la imagen a eliminar.
     * @return true si la eliminación fue exitosa, false si no se pudo eliminar.
     */
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

    /**
     * Lee los metadatos de una imagen en el almacenamiento y devuelve su tipo MIME.
     *
     * @param referencia Referencia de la imagen en el almacenamiento.
     * @return Tipo MIME de la imagen o null si no se pueden leer los metadatos.
     */
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

    /**
     * Obtiene las URLs de descarga de todas las imágenes en un directorio del almacenamiento.
     *
     * @param dir Directorio en el almacenamiento.
     * @return Lista de URI de descarga de las imágenes.
     */
    suspend fun getAllImages(dir: String): List<Uri>{
        val reference: StorageReference = storage.reference.child("picture/$dir")

        val result: List<Uri> = reference.listAll().await().items.map{it.downloadUrl.await()}
        return result
    }
}
