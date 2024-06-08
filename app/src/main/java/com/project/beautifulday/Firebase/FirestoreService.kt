package com.project.beautifulday.Firebase

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.firestore.FirebaseFirestore
import com.project.beautifulday.Cocktail.ui.States.CocktailUser
import com.project.beautifulday.Local
import com.project.beautifulday.Meal.ui.States.MealUser
import com.project.beautifulday.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FirestoreService@Inject constructor(private val fireStore: FirebaseFirestore) {

    // Función para crear un nuevo usuario en Firestore
    fun createUser(user: User) {
        fireStore.collection("Users")
            .add(user)
            .addOnSuccessListener {
                Log.d("GUARDAR OK", "SE GUARDO CORRECTAMENTE")
            }
            .addOnFailureListener {
                Log.d("ERROR", "ERROR AL GUARDAR")
            }
    }

    // Función para obtener una lista de comidas asociadas a un correo electrónico
    suspend fun fetchMeal(email: String?): List<MealUser> {
        return withContext(Dispatchers.IO) {
            val documents = mutableListOf<MealUser>()
            try {
                val querySnapshot = fireStore.collection("Meals")
                    .whereEqualTo("emailUser", email.toString())
                    .get()
                    .await()

                for (document in querySnapshot.documents) {
                    val myDocument =
                        document.toObject(MealUser::class.java)?.copy(idDocument = document.id)
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

    // Función para obtener una lista de cócteles asociados a un correo electrónico
    suspend fun fetchCocktail(email: String?): List<CocktailUser> {
        return withContext(Dispatchers.IO) {
            val documents = mutableListOf<CocktailUser>()
            try {
                val querySnapshot = fireStore.collection("Cocktails")
                    .whereEqualTo("emailUser", email.toString())
                    .get()
                    .await()

                for (document in querySnapshot.documents) {
                    val myDocument =
                        document.toObject(CocktailUser::class.java)?.copy(idDocument = document.id)
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

    // Función para obtener una lista de locales desde una colección específica
    suspend fun fetchLocal(colec: String): List<Local> {
        return withContext(Dispatchers.IO) {
            val documents = mutableListOf<Local>()
            try {
                val querySnapshot = fireStore.collection(colec)
                    .get()
                    .await()

                for (document in querySnapshot.documents) {
                    val myDocument =
                        document.toObject(Local::class.java)?.copy(idDocument = document.id)
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

    // Función para obtener una lista de comidas desde una colección específica
    suspend fun fetchMealCreater(colec: String): List<MealUser> {
        return withContext(Dispatchers.IO) {
            val documents = mutableListOf<MealUser>()
            try {
                val querySnapshot = fireStore.collection(colec)
                    .get()
                    .await()

                for (document in querySnapshot.documents) {
                    val myDocument =
                        document.toObject(MealUser::class.java)?.copy(idDocument = document.id)
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

    /**
     * Función para obtener una lista de cócteles desde una colección específica.
     *
     * @param colec Colección en Firestore desde donde se obtienen los cócteles.
     * @return Lista de [CocktailUser] obtenida de Firestore.
     */
    suspend fun fetchCocktailCreater(colec: String): List<CocktailUser> {
        return withContext(Dispatchers.IO) {
            val documents = mutableListOf<CocktailUser>()
            try {
                val querySnapshot = fireStore.collection(colec)
                    .get()
                    .await()

                for (document in querySnapshot.documents) {
                    val myDocument =
                        document.toObject(CocktailUser::class.java)?.copy(idDocument = document.id)
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

    /**
     * Función para obtener un usuario desde Firestore basado en su correo electrónico.
     *
     * @param email Correo electrónico del usuario que se va a buscar.
     * @return Objeto [User] obtenido de Firestore.
     * @throws IllegalArgumentException Si el email es nulo o vacío.
     * @throws Exception Si ocurre un error al realizar la consulta a Firestore o si el usuario no se encuentra.
     */
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


    /**
     * Función para obtener una comida por su ID desde Firestore.
     *
     * @param documento ID del documento que se va a obtener.
     * @param colec Colección en Firestore donde se encuentra el documento.
     * @return Objeto [MealUser] correspondiente al ID especificado, o null si no se encuentra.
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

    /**
     * Función para obtener un cóctel por su ID desde Firestore.
     *
     * @param documento ID del documento que se va a obtener.
     * @param colec Colección en Firestore donde se encuentra el documento.
     * @return Objeto [CocktailUser] correspondiente al ID especificado, o null si no se encuentra.
     */
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

    /**
     * Función para obtener un local por su ID desde Firestore.
     *
     * @param documento ID del documento que se va a obtener.
     * @param colec Colección en Firestore donde se encuentra el documento.
     * @return Objeto [Local] correspondiente al ID especificado, o null si no se encuentra.
     */
    suspend fun getLocalById(documento: String, colec: String): Local? {
        return withContext(Dispatchers.IO) {
            try {
                val documentSnapshot = fireStore.collection(colec)
                    .document(documento)
                    .get()
                    .await()
                documentSnapshot.toObject(Local::class.java)
            } catch (e: Exception) {
                Log.d("ERROR", "Error al obtener el documento: ${e.message}")
                null
            }
        }
    }

    /**
     * Función para guardar una nueva comida en Firestore.
     *
     * @param colec Colección en Firestore donde se almacenará la comida.
     * @param id Nombre del campo que se va a verificar para evitar duplicados.
     * @param idMeal ID de la comida que se va a guardar.
     * @param meal Objeto [MealUser] que representa la comida a guardar.
     * @param email Correo electrónico asociado a la comida.
     * @param context Contexto de la actividad desde donde se llama a la función.
     * @return [Task<Boolean>] que indica si la operación de guardado fue exitosa o no.
     */
    fun saveNewMeal(
        colec: String,
        id: String,
        idMeal: String?,
        meal: MealUser,
        email: String?,
        context: ComponentActivity
    ): Task<Boolean> {
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
                val existingMeals =
                    querySnapshot.documents.mapNotNull { it.toObject(MealUser::class.java) }
                val emailExists = existingMeals.any { it.emailUser == email }

                if (emailExists) {
                    result.setResult(false)
                    Toast.makeText(
                        context,
                        "Registro existente con el mismo email e ID de comida",
                        Toast.LENGTH_SHORT
                    ).show()
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


    /**
     * Guarda un nuevo cóctel en Firestore.
     *
     * @param colec Colección en Firestore donde se almacenará el cóctel.
     * @param id Nombre del campo utilizado para la verificación de duplicados.
     * @param idDrink ID del cóctel que se va a guardar.
     * @param cocktail Objeto [CocktailUser] que representa el cóctel a guardar.
     * @param email Correo electrónico asociado al cóctel.
     * @param context Contexto de la actividad desde donde se llama a la función.
     * @return [Task<Boolean>] que indica si la operación de guardado fue exitosa o no.
     */
    fun saveNewCocktail(
        colec: String,
        id: String,
        idDrink: String?,
        cocktail: CocktailUser,
        email: String?,
        context: ComponentActivity
    ): Task<Boolean> {
        val result = TaskCompletionSource<Boolean>()

        if (idDrink == null || email.isNullOrEmpty()) {
            result.setResult(false)
            Log.d("ERROR", "idDrink o email es nulo/vacío")
            return result.task
        }

        fireStore.collection(colec)
            .whereEqualTo(id, idDrink)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val existingMeals =
                    querySnapshot.documents.mapNotNull { it.toObject(CocktailUser::class.java) }
                val emailExists = existingMeals.any { it.emailUser == email }

                if (emailExists) {
                    result.setResult(false)
                    Toast.makeText(
                        context,
                        "Registro existente con el mismo email e ID de cocktail",
                        Toast.LENGTH_SHORT
                    ).show()
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

    /**
     * Guarda un nuevo local en Firestore.
     *
     * @param colec Colección en Firestore donde se almacenará el local.
     * @param id Nombre del campo utilizado para la verificación de duplicados.
     * @param idLocal ID del local que se va a guardar.
     * @param local Objeto [Local] que representa el local a guardar.
     * @param email Correo electrónico asociado al local.
     * @param context Contexto de la actividad desde donde se llama a la función.
     * @return [Task<Boolean>] que indica si la operación de guardado fue exitosa o no.
     */
    fun saveNewLocal(
        colec: String,
        id: String,
        idLocal: String?,
        local: Local,
        email: String?,
        context: ComponentActivity
    ): Task<Boolean> {
        val result = TaskCompletionSource<Boolean>()

        if (idLocal == null || email.isNullOrEmpty()) {
            result.setResult(false)
            Log.d("ERROR", "idLocal o email es nulo/vacío")
            return result.task
        }

        fireStore.collection(colec)
            .whereEqualTo(id, idLocal)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val existingMeals =
                    querySnapshot.documents.mapNotNull { it.toObject(Local::class.java) }
                val emailExists = existingMeals.any { it.emailUser == email }

                if (emailExists) {
                    result.setResult(false)
                    Toast.makeText(
                        context,
                        "Registro existente con el mismo email e ID de comida",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("ERROR", "EL REGISTRO YA EXISTE")
                } else {
                    fireStore.collection(colec)
                        .add(local)
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

    /**
     * Actualiza las estrellas de una comida en Firestore.
     *
     * @param colec Colección en Firestore donde se encuentra la comida.
     * @param iDoc ID del documento que contiene la comida.
     * @param meal Objeto [MealUser] que representa la comida a actualizar.
     * @return [Task<Boolean>] que indica si la operación de actualización fue exitosa o no.
     */
    fun updateStarsMeal(colec: String, iDoc: String, meal: MealUser): Task<Boolean> {
        val result = TaskCompletionSource<Boolean>()

        val plusVotes = hashMapOf(
            "points" to meal.points,
            "votes" to meal.votes,
            "listVotes" to meal.listVotes
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

    /**
     * Actualiza las estrellas de un cóctel en Firestore.
     *
     * @param colec Colección en Firestore donde se encuentra el cóctel.
     * @param iDoc ID del documento que contiene el cóctel.
     * @param drink Objeto [CocktailUser] que representa el cóctel a actualizar.
     * @return [Task<Boolean>] que indica si la operación de actualización fue exitosa o no.
     */
    fun updateStarsCocktail(colec: String, iDoc: String, drink: CocktailUser): Task<Boolean> {
        val result = TaskCompletionSource<Boolean>()

        val plusVotes = hashMapOf(
            "puntuacion" to drink.puntuacion,
            "votes" to drink.votes,
            "listVotes" to drink.listVotes
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

    /**
     * Actualiza las estrellas de un local en Firestore.
     *
     * @param colec Colección en Firestore donde se encuentra el local.
     * @param iDoc ID del documento que contiene el local.
     * @param local Objeto [Local] que representa el local a actualizar.
     * @return [Task<Boolean>] que indica si la operación de actualización fue exitosa o no.
     */
    fun updateStarsLocalM(
        colec: String, iDoc: String,
        local: Local
    ): Task<Boolean> {
        val result = TaskCompletionSource<Boolean>()

        val plusVotes = hashMapOf(
            "puntuacion" to local.puntuacion,
            "votes" to local.votes,
            "listVotes" to local.listVotes
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

    /**
     * Elimina un registro de Firestore.
     *
     * @param colec Colección en Firestore de donde se eliminará el registro.
     * @param documento ID del documento que se va a eliminar.
     * @return [Task<Boolean>] que indica si la operación de eliminación fue exitosa o no.
     */
    fun deleteRegister(colec: String, documento: String): Task<Boolean> {
        val result = TaskCompletionSource<Boolean>()

        fireStore.collection(colec)
            .document(documento)
            .delete()
            .addOnSuccessListener {
                result.setResult(true)
                Log.d("Exito al borrar", "Cocktail borrado con éxito")
            }
            .addOnFailureListener {
                result.setResult(false)
                Log.d("Error al borrar", "No se pudo borrar el cocktail.")
            }
        return result.task
    }
}
