package com.project.beautifulday.Firebase



import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Clase que proporciona servicios de autenticación utilizando Firebase Authentication.
 *
 * @property firebaseAuth Instancia de FirebaseAuth proporcionada por inyección de dependencias.
 */
class AuthService @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    /**
     * Realiza el inicio de sesión de un usuario utilizando su nombre de usuario y contraseña.
     *
     * @param user Nombre de usuario o dirección de correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return Objeto FirebaseUser correspondiente al usuario que ha iniciado sesión, o null si el inicio de sesión falla.
     */
    suspend fun login(user: String, password: String): FirebaseUser? {
        return firebaseAuth.signInWithEmailAndPassword(user, password).await().user
    }

    /**
     * Registra un nuevo usuario utilizando su dirección de correo electrónico y contraseña.
     *
     * @param email Dirección de correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return Objeto FirebaseUser correspondiente al nuevo usuario registrado, o null si el registro falla.
     */
    suspend fun register(email: String, password: String): FirebaseUser? {
        return suspendCancellableCoroutine { cancellableContinuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                val user: FirebaseUser? = it.user
                cancellableContinuation.resume(user)
            }.addOnFailureListener{
                cancellableContinuation.resumeWithException(it)
            }
        }
    }

    /**
     * Verifica si hay un usuario autenticado actualmente.
     *
     * @return true si hay un usuario autenticado, false en caso contrario.
     */
    fun isUserLogged(): Boolean {
        return getCurrentUser() != null
    }

    /**
     * Obtiene el objeto FirebaseUser del usuario autenticado actualmente.
     *
     * @return Objeto FirebaseUser correspondiente al usuario autenticado actualmente, o null si no hay ningún usuario autenticado.
     */
    fun getCurrentUser() = firebaseAuth.currentUser

    suspend fun updatePassword(newPassword: String, context: ComponentActivity) {
        val currentUser = getCurrentUser()
        if (currentUser != null) {
            withContext(Dispatchers.IO) {
                currentUser.updatePassword(newPassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                context,
                                "Actualización de usuario realizada",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "No se pudo actualizar: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(
                            context,
                            "Error al actualizar: ${exception.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        } else {
            Toast.makeText(
                context,
                "Usuario no autenticado",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Función para eliminar un usuario
    suspend fun deleteCurrentUser(context:ComponentActivity) {
        val currentUser = getCurrentUser()

        currentUser?.let { user ->
            try {
                user.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Usuario eliminado correctamente
                            // Aquí puedes mostrar un mensaje al usuario o realizar otra acción
                            Toast.makeText(
                                context,
                                "Usuario eliminado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            // No se pudo eliminar el usuario, muestra un mensaje de error
                            Toast.makeText(
                                context,
                                "No se pudo eliminar el usuario: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } catch (e: Exception) {
                // Captura cualquier excepción
                Toast.makeText(
                    context,
                    "Error al intentar eliminar el usuario: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    fun logOut() = firebaseAuth.signOut()

    /**
     * Obtiene el ID del usuario autenticado actualmente.
     *
     * @return ID del usuario autenticado actualmente, o null si no hay ningún usuario autenticado.
     */
    fun id() = firebaseAuth.currentUser?.uid

    /**
     * Obtiene el correo electrónico del usuario autenticado actualmente.
     *
     * @return Correo electrónico del usuario autenticado actualmente, o null si no hay ningún usuario autenticado.
     */
    fun email() = firebaseAuth.currentUser?.email
}
