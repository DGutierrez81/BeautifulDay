package com.project.beautifulday.Firebase

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.project.beautifulday.Cocktail.ui.States.CocktailUser
import com.project.beautifulday.Meal.ui.States.MealUser
import com.project.beautifulday.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FirestoreService@Inject constructor(private val fireStore: FirebaseFirestore){
     fun createUser(user: User){

        fireStore.collection("Users")
            .add(user)
            .addOnSuccessListener {
                Log.d("GUARDAR OK", "SE GUARDO CORRECTAMENTE")
            }
            .addOnFailureListener {
                Log.d("ERROR", "ERROR AL GUARDAR")
            }
    }

    suspend fun fetchMeal(email: String?): List<MealUser> {
        return withContext(Dispatchers.IO) {
            val documents = mutableListOf<MealUser>()
            try {
                val querySnapshot = fireStore.collection("Meals")
                    .whereEqualTo("emailUser", email.toString())
                    .get()
                    .await()

                for (document in querySnapshot.documents) {
                    val myDocument = document.toObject(MealUser::class.java)?.copy(idDocument = document.id)
                    if (myDocument != null) {
                        documents.add(myDocument)
                    }
                }
            } catch (e: Exception) {
                Log.d("ERROR", "NO SE PUEDE ACCEDER AL REGISTRO: ${e.message}")
            }
            documents
        }
    }

    suspend fun fetchCocktail(email: String?): List<CocktailUser> {
        return withContext(Dispatchers.IO) {
            val documents = mutableListOf<CocktailUser>()
            try {
                val querySnapshot = fireStore.collection("Cocktails")
                    .whereEqualTo("emailUser", email.toString())
                    .get()
                    .await()

                for (document in querySnapshot.documents) {
                    val myDocument = document.toObject(CocktailUser::class.java)?.copy(idDocument = document.id)
                    if (myDocument != null) {
                        documents.add(myDocument)
                    }
                }
            } catch (e: Exception) {
                Log.d("ERROR", "NO SE PUEDE ACCEDER AL REGISTRO: ${e.message}")
            }
            documents
        }
    }

    suspend fun fetchMealCreater(): List<MealUser> {
        return withContext(Dispatchers.IO) {
            val documents = mutableListOf<MealUser>()
            try {
                val querySnapshot = fireStore.collection("CreateMeals")
                    .get()
                    .await()

                for (document in querySnapshot.documents) {
                    val myDocument = document.toObject(MealUser::class.java)?.copy(idDocument = document.id)
                    if (myDocument != null) {
                        documents.add(myDocument)
                    }
                }
            } catch (e: Exception) {
                Log.d("ERROR", "NO SE PUEDE ACCEDER AL REGISTRO: ${e.message}")
            }
            documents
        }
    }

    suspend fun fetchCocktailCreater(): List<CocktailUser> {
        return withContext(Dispatchers.IO) {
            val documents = mutableListOf<CocktailUser>()
            try {
                val querySnapshot = fireStore.collection("CreateCocktails")
                    .get()
                    .await()

                for (document in querySnapshot.documents) {
                    val myDocument = document.toObject(CocktailUser::class.java)?.copy(idDocument = document.id)
                    if (myDocument != null) {
                        documents.add(myDocument)
                    }
                }
            } catch (e: Exception) {
                Log.d("ERROR", "NO SE PUEDE ACCEDER AL REGISTRO: ${e.message}")
            }
            documents
        }
    }

    suspend fun fetchUser(email: String?): User {
        // Verifica si el email es nulo o vacío
        require(!email.isNullOrEmpty()) { "Email must not be null or empty" }

        // Usa el contexto IO para operaciones de red o base de datos
        return withContext(Dispatchers.IO) {
            val firestore = FirebaseFirestore.getInstance()

            // Inicializa una variable para el documento de usuario
            var user: User? = null

            try {
                // Realiza la consulta a Firestore
                val querySnapshot = firestore.collection("Users")
                    .whereEqualTo("email", email)
                    .get()
                    .await()

                // Si se encontró un documento, convierte el documento a un objeto User
                if (querySnapshot.documents.isNotEmpty()) {
                    val documentSnapshot = querySnapshot.documents[0]
                    user = documentSnapshot.toObject(User::class.java)
                } else {
                    // Log para depuración si no se encuentra el usuario
                    Log.e("fetchUser", "User with email $email not found")
                }
            } catch (e: Exception) {
                // Maneja la excepción si ocurre algún error en la consulta
                Log.e("fetchUser", "Error fetching user: ${e.message}", e)
                throw Exception("Failed to fetch user: ${e.message}")
            }

            // Verifica si el usuario es nulo y lanza una excepción si es necesario
            user ?: throw Exception("User with email $email not found")
        }
    }




/*
    fun fetchMeal(email: String?): MutableList<MealUser>{
        val documents = mutableListOf<MealUser>()
        fireStore.collection("Meals")
            .whereEqualTo("emailUser", email.toString())
            .addSnapshotListener{ querySnapshot, error ->
                if(error != null) {
                    Log.d("ERROR", "NO SE PUEDE")
                    return@addSnapshotListener
                }
                if(querySnapshot != null) {
                    for(document in querySnapshot){
                        val myDocument =
                            document.toObject(MealUser::class.java).copy(idDocument = document.id)
                        documents.add(myDocument)
                    }
                }
            }
        return documents
    }

 */

    suspend fun getMealById(documento: String, colec: String): MealUser? {
        return withContext(Dispatchers.IO) {
            try {
                val documentSnapshot = fireStore.collection(colec)
                    .document(documento)
                    .get()
                    .await()
                documentSnapshot.toObject(MealUser::class.java)
            } catch (e: Exception) {
                Log.d("ERROR", "Error al obtener el documento: ${e.message}")
                null
            }
        }
    }

    suspend fun getCocktailById(documento: String, colec: String): CocktailUser? {
        return withContext(Dispatchers.IO) {
            try {
                val documentSnapshot = fireStore.collection(colec)
                    .document(documento)
                    .get()
                    .await()
                documentSnapshot.toObject(CocktailUser::class.java)
            } catch (e: Exception) {
                Log.d("ERROR", "Error al obtener el documento: ${e.message}")
                null
            }
        }
    }

    /*
    fun getMealById(documento: String): MealUser{
        var mealUser = MealUser()
        fireStore.collection("Meals")
            .document(documento)
            .addSnapshotListener { querySnapshot, error ->
                if(error != null) {
                    return@addSnapshotListener
                }
                if(querySnapshot != null){
                    val meal = querySnapshot.toObject(MealUser::class.java)
                    mealUser = mealUser.copy(
                        idMeal = meal?.idMeal,
                        strMeal = meal?.strMeal,
                        strCategory = meal?.strCategory,
                        strArea = meal?.strArea,
                        strInstructions = meal?.strInstructions,
                        strMealThumb = meal?.strMealThumb,
                        strTags = meal?.strTags,
                        strYoutube = meal?.strYoutube,
                        strIngredients = meal?.strIngredients,
                        strMeasures = meal?.strMeasures,
                        emailUser = meal?.emailUser,
                        idDocument = meal?.idDocument
                    )
                }
            }
        return mealUser
    }

     */

    fun saveNewMeal(colec: String, id: String, idMeal: String?, meal: MealUser, email: String?, context: ComponentActivity): Task<Boolean> {
        val result = TaskCompletionSource<Boolean>()

        if (idMeal == null || email.isNullOrEmpty()) {
            result.setResult(false)
            Log.d("ERROR", "idMeal o email es nulo/vacío")
            return result.task
        }

        fireStore.collection(colec)
            .whereEqualTo(id, idMeal)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val existingMeals = querySnapshot.documents.mapNotNull { it.toObject(MealUser::class.java) }
                val emailExists = existingMeals.any { it.emailUser == email }

                if (emailExists) {
                    result.setResult(false)
                    Toast.makeText(context, "Registro existente con el mismo email e ID de comida", Toast.LENGTH_SHORT).show()
                    Log.d("ERROR", "EL REGISTRO YA EXISTE")
                } else {
                    fireStore.collection(colec)
                        .add(meal)
                        .addOnSuccessListener {
                            result.setResult(true)
                            Log.d("GUARDAR OK", "EL REGISTRO SE GUARDO CORRECTAMENTE")
                        }
                        .addOnFailureListener {
                            result.setResult(false)
                            Log.d("ERROR AL GUARDAR", "ERROR AL GUARDAR EL REGISTRO")
                        }
                }
            }
            .addOnFailureListener {
                result.setResult(false)
                Log.d("ERROR AL VERIFICAR", "ERROR AL VERIFICAR SI EL REGISTRO EXISTE")
            }

        return result.task
    }


    fun saveNewCocktail(colec: String, id: String, idDrink: String?, cocktail: CocktailUser, email: String?,context: ComponentActivity): Task<Boolean> {
        val result = TaskCompletionSource<Boolean>()

        if (idDrink == null || email.isNullOrEmpty()) {
            result.setResult(false)
            Log.d("ERROR", "idMeal o email es nulo/vacío")
            return result.task
        }

        fireStore.collection(colec)
            .whereEqualTo(id, idDrink)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val existingMeals = querySnapshot.documents.mapNotNull { it.toObject(MealUser::class.java) }
                val emailExists = existingMeals.any { it.emailUser == email }

                if (emailExists) {
                    result.setResult(false)
                    Toast.makeText(context, "Registro existente con el mismo email e ID de comida", Toast.LENGTH_SHORT).show()
                    Log.d("ERROR", "EL REGISTRO YA EXISTE")
                } else {
                    fireStore.collection(colec)
                        .add(cocktail)
                        .addOnSuccessListener {
                            result.setResult(true)
                            Log.d("GUARDAR OK", "EL REGISTRO SE GUARDO CORRECTAMENTE")
                        }
                        .addOnFailureListener {
                            result.setResult(false)
                            Log.d("ERROR AL GUARDAR", "ERROR AL GUARDAR EL REGISTRO")
                        }
                }
            }
            .addOnFailureListener {
                result.setResult(false)
                Log.d("ERROR AL VERIFICAR", "ERROR AL VERIFICAR SI EL REGISTRO EXISTE")
            }

        return result.task
    }

    fun updateStarsMeal(colec: String, iDoc: String, meal: MealUser): Task<Boolean> {
        val result = TaskCompletionSource<Boolean>()

        val plusVotes = hashMapOf(
            "points" to meal.points,
            "votes" to meal.votes
        )
        fireStore.collection(colec).document(iDoc)
            .update(plusVotes as Map<String, Any>)
            .addOnSuccessListener {
                result.setResult(true)
                Log.d("Actualizacion OK", "Se ha actualizado correctamente")
            }
            .addOnFailureListener {
                result.setResult(false)
                Log.d("Error al actualizar", "No se ha podido realizar la actualización.")
            }

        return result.task
    }


    fun updateStarsCocktail(colec: String, iDoc: String, drink: CocktailUser): Task<Boolean> {
        val result = TaskCompletionSource<Boolean>()

        val plusVotes = hashMapOf(
            "puntuacion" to drink.puntuacion,
            "votes" to drink.votes
        )
        fireStore.collection(colec).document(iDoc)
            .update(plusVotes as Map<String, Any>)
            .addOnSuccessListener {
                result.setResult(true)
                Log.d("Actualizacion OK", "Se ha actualizado correctamente")
            }
            .addOnFailureListener {
                result.setResult(false)
                Log.d("Error al actualizar", "No se ha podido realizar la actualización.")
            }

        return result.task
    }

    fun deleteRegister(colec: String, documento: String): Task<Boolean>{
        val result = TaskCompletionSource<Boolean>()

        fireStore.collection(colec)
            .document(documento)
            .delete()
            .addOnSuccessListener {
                result.setResult(true)
                Log.d("Exito al borrar", "Cocktail borrado con éxito")
            }
            .addOnFailureListener{
                result.setResult(false)
                Log.d("Error al borrar", "No se pudo borrar el cocktail.")
            }
        return result.task
    }




    /*
    suspend fun updateStars(iDoc: String, meal: MealUser): MealUser {
        return withContext(Dispatchers.IO) {
            val document = MealUser()
            try {
                val plusVotes = hashMapOf(
                    "points" to meal.points,
                    "votes" to meal.votes
                )
                fireStore.collection("Meals").document(iDoc)
                    .update(plusVotes as Map<String, Any>)
                    .await()  // Espera el resultado de la actualización

                Log.d("Actualizacion OK", "Se ha actualizado correctamente")
            } catch (e: Exception) {
                Log.d("Error al actualizar", "No se ha podido realizar la actualización.", e)
            }
            document
        }
    }

     */




}

