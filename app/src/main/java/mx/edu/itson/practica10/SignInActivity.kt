package mx.edu.itson.practica10

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.play.integrity.internal.ac
import com.google.firebase.auth.FirebaseAuth
import mx.edu.itson.practica10.databinding.ActivitySignInBinding


class SignInActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "SignInActivity"
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        auth = FirebaseAuth.getInstance()

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInAppCompatButton.setOnClickListener{
            val mEail = binding.emailEditText.text.toString()
            val mPassword = binding.passwordEditText.text.toString()

            when{
                mEail.isEmpty() || mPassword.isEmpty() -> {
                    Toast.makeText(baseContext,"Mail o Password incorrectos.", Toast.LENGTH_SHORT).show()
                }else -> {
                SignIn(mEail, mPassword)
            }
            }
        }
    }

    private fun SignIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    reload()
                } else {
                    Log.w(TAG, "signInWithEmail:failure")
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun reload(){
        val intent= Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }
}