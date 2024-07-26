package com.ajtechnology.chatty

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ajtechnology.chatty.data.Events
import com.ajtechnology.chatty.data.USER_NODE
import com.ajtechnology.chatty.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    val auth: FirebaseAuth, val db: FirebaseFirestore, val storage: FirebaseStorage
) : ViewModel() {


    var inProgress = mutableStateOf(false)
    var eventMutableState = mutableStateOf<Events<String>?>(null)
    var signIn = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)

    init {
        val currentUser = auth.currentUser
        signIn.value = currentUser !=null
        currentUser?.uid.let {
            if (it != null) {
                getUserData(it)
            }
        }
    }

    fun loggedIn(email: String,password: String){

        if (email.isEmpty() or password.isEmpty()){
            handleException(customMessage = "Please Fill the all Fields")
            return
        }
        else{
            inProgress.value = true
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                    signIn.value = true
                    inProgress.value = false
                    auth.currentUser?.uid?.let {it1->
                        getUserData(it1)
                    }
                }
            }
        }
    }
    fun signUp(name: String, number: String, email: String, password: String) {
        inProgress.value = true
        if(name.isEmpty() or number.isEmpty() or email.isEmpty() or password.isEmpty()){
            handleException(customMessage = "Please fill all fields")
            inProgress.value = false
            return
        }
        db.collection(USER_NODE).whereEqualTo("number",number).get().addOnSuccessListener {
            if (it.isEmpty){
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

                    if (it.isSuccessful) {
                        signIn.value = true
                        createOrUpdateProfile(name, number)
                        Timber.i("signUP:User Logged In")
                    } else {
                        handleException(it.exception, "Sign up Failed")

                    }
                }
            }else{
                handleException(customMessage = "Number already ex")
            }
        }

    }

    private fun createOrUpdateProfile(
        name: String? = null,
        number: String? = null,
        imageUrl: String? = null
    ) {
        var uid = auth.currentUser?.uid
        val userData = UserData(
            userid = uid,
            name = name ?: userData.value?.name,
            number = number ?: userData.value?.number,
            imageUrl = imageUrl ?: userData.value?.imageUrl
        )

        uid?.let {
            inProgress.value = true
            db.collection(USER_NODE).document(uid).get().addOnSuccessListener {

                if (it.exists()) {


                } else {
                    db.collection(USER_NODE).document(uid).set(userData)
                    inProgress.value = false
                    getUserData(uid)
                }
            }.addOnFailureListener {
                handleException(it, "Cannot Retrieve User Data")
            }
        }

    }


    private fun getUserData(uid: String) {
        inProgress.value = true
        db.collection(USER_NODE).document(uid).addSnapshotListener { value, error ->
            if (error != null) {
                handleException(error, "Failed to retrieve user data")
            }
            if (value != null) {
                val user = value.toObject<UserData>()
                userData.value = user
                inProgress.value = false
            }
        }
    }


    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        Timber.e("Chatty App Exception: $exception")
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage

        val message = if (customMessage.isEmpty()) errorMsg else customMessage

        eventMutableState.value = Events(message!!)
        inProgress.value = false

    }

    fun uploadProfileImage(uri: Uri) {
        uploadImage(uri){
          createOrUpdateProfile(imageUrl = it.toString())
        }
    }

    private fun uploadImage(uri: Uri,onSuccess: (Uri) -> Unit) {
        inProgress.value = true
        val storageRef = storage.reference
        val uuid = UUID.randomUUID()
        val imageRef = storageRef.child("image/$uuid")
        val uploadTask = imageRef.putFile(uri).addOnSuccessListener {
            val result = it.metadata?.reference?.downloadUrl
            result?.addOnSuccessListener(onSuccess)
            inProgress.value = false
        }
            .addOnFailureListener {
                handleException(it)
            }

    }

}