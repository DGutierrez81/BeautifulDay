package com.project.beautifulday.Firebase



import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthService@Inject constructor(private val firebaseAuth: FirebaseAuth) {

    suspend fun login(user: String, password: String): FirebaseUser? {
        return firebaseAuth.signInWithEmailAndPassword(user, password).await().user
    }

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

    fun logOut() = firebaseAuth.signOut()

    fun id() = firebaseAuth.currentUser?.uid
    fun email() = firebaseAuth.currentUser?.email

}