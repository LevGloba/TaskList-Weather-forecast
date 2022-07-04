package com.example.diplom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.diplom.databinding.ActivityAuthenticatingBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider



class Authenticating : AppCompatActivity() {

    //объявление переменных
    lateinit var launcher: ActivityResultLauncher<Intent>
    lateinit var binding: ActivityAuthenticatingBinding

    //переопределение функции onCreate
    override fun onCreate(savedInstanceState: Bundle?) {

        //подключение посредника базы данных
        initFirebase()

        //отрисовка визуального отображения на дисплее пользователя
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //проверяет отправку данных на базу данныз
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if(account != null) fireBaseAuWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.d("MyLog", "Ошибка при подключении: $e")
            }
        }
        //вызывает функцию для проверки авторизации
        checkAuthState()
        //слушатель нажатии кнопки "Регистрация"
        binding.buttonAuthenticatingNumber.setOnClickListener {
            singInWithGoogle()
        }
    }
    //отрисовка списка пользователя
    private fun getClient(): GoogleSignInClient{
        try {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return  GoogleSignIn.getClient(this, gso)
        } catch (e: Exception) {
            Toast.makeText(this, "Google account\n отсутсвует", Toast.LENGTH_SHORT).show()
        }
    }
    //вызывает функцию для отрисовки списка пользователя
    //записывает в глобальную переменную данных пользователя
    private fun singInWithGoogle() {
        val singInClient = getClient()
        launcher.launch(singInClient.signInIntent)
    }
    //проверка на успешную регистрацию пользователя
    private fun fireBaseAuWithGoogle(idToken: String)
    {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful) {
                Log.d("MyLog", "Регистрация прошла успешно")
                checkAuthState()
            }
            else Log.d("MyLog", "Ошибка!")
        }
    }
    //вызывает функцию для проверки авторизации и вызывает новый класс
    private fun checkAuthState() {
        if(firebaseAuth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}