package com.example.tvlonwer.viewModel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth
    var data = MutableLiveData<Boolean>()


    public  fun forgetPassword(email: String) {
        // can be launched in a separate asynchronous job
        auth = FirebaseAuth.getInstance()
        if (email.contains("@")) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val d = Log.d("Forget", "Email sent.")
                        data.postValue(true)

                    } else {
                        /*val show: Any = Toast.makeText(
                            this,
                            "Failed to send reset email!",
                            Toast.LENGTH_SHORT
                        ).show()*/
                        Log.d("Forget", "Email not sent.")
                        data.postValue(false)
                    }
                }
        } else {
            if (!email.contains("@")) {
                // email = "Incorrect email format"
                Log.d("Forget", "incorrect email.")
                data.postValue(false)
            }


        }
    }
}