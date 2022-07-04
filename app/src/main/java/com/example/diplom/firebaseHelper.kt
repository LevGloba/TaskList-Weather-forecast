package com.example.diplom

//импортирование зависимости
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

//регистрация переменных базы данных
lateinit var firebaseAuth: FirebaseAuth
lateinit var REF_DATABASE_ROOT: DatabaseReference

//константы
const val NODE_USERS = "Users"
const val NODE_TASK ="Task"
const val API_KEY = "4043f9adaff843f1bad92002220606"
const val NODE_TEMP = "°C"
const val NODE_HTTP = "https://"

//функция для вызова функции базы данных
fun initFirebase() {
    firebaseAuth = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT =FirebaseDatabase.getInstance().reference
}