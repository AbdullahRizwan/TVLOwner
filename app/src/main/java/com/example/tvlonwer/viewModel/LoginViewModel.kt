package com.example.tvlowner.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth
    var data = MutableLiveData<FirebaseUser>()


    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    data.postValue(auth.currentUser)
                }
                else{
                    data.postValue(null)
                }
            }


    }
}