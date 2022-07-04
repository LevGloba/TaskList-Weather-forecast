package com.example.diplom

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import java.text.SimpleDateFormat
import java.util.*
import com.example.diplom.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

//объявление глобальных перемен
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: TaskAdapter


//Отрисовывает пользовательский интерфейс
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//вызывает посредника базы данных
        initFirebase()
    
//слушатель нажатия кнопки с иконкой +
        binding.imageAddTask.setOnClickListener {
            //вызов и отрисовка макета AlertDialog
            val mDialogView = layoutInflater.inflate(R.layout.alert_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Задача")
            val mAlertDialog = mBuilder.show()
            //Слушатель нажатия кнопки  Ok
            mDialogView.findViewById<ImageButton>(R.id.buttonPosistiv).setOnClickListener {
                val editTitleTask: String = mDialogView.findViewById<EditText>(R.id.editTitele).text.toString()
                val editTask: String = mDialogView.findViewById<EditText>(R.id.editTask).text.toString()
                //проверка на пустой ввод
                if (editTitleTask != "" || editTask != "") {
                    var currentTime = Date()
                    var dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                    var dateText = dateFormat.format(currentTime)
                    val sdf = SimpleDateFormat("dd MM yyyy hh mm ss")
                    val currentDate = sdf.format(currentTime)

                    //сохраняет введенные данные пользователя в базе данных
                    REF_DATABASE_ROOT.child(NODE_USERS).child("${firebaseAuth.uid}").child(NODE_TASK)
                        .child(currentDate.toString())
                        .setValue(Task(currentDate.toString(),editTitleTask,editTask, dateText.toString()))
                    mAlertDialog.dismiss()
                        //если поля пустые
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Пустые поля!\nПожалуйста заполните одно из полей.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }//слушатель нажатия кнопки Cancle закрывает вызваное окно
            mDialogView.findViewById<ImageButton>(R.id.buttonNegativ).setOnClickListener {
                mAlertDialog.dismiss()
            }
        }
//вызывает функцию
        onChangeListener(REF_DATABASE_ROOT,firebaseAuth)
        initRcView()
//слушатель нажатия кнопки с иконкой облоко и выход
        binding.weather.setOnClickListener {
            //вызывает функцию
            onMoveWeather() }
        binding.ImgExitButton.setOnClickListener {
            //вызывает функцию
            onExit() }
    }
//функция вызывает и отрисовывает Список
    private fun initRcView() = with(binding) {
        adapter = TaskAdapter()
        rcView.layoutManager = LinearLayoutManager(this@MainActivity)
        rcView.adapter = adapter
    }

    //функция выхода из аккаунта
    fun onExit(){
        firebaseAuth.signOut()
        finish()
    }
//функция перехода на другой класс "weatherActivity"
    fun onMoveWeather() {
        val intent = Intent(this, WeatherActivity::class.java)
        startActivity(intent)
        finish()
    }

// загружает данные пользователя с базы данных проверяет на пустоту и отправляет в класс Task
    private fun onChangeListener(REF_DATABASE_ROOT: DatabaseReference, firebaseAuth: FirebaseAuth) {
        REF_DATABASE_ROOT.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = ArrayList<Task>()
                    for (i in snapshot.child(NODE_USERS).child("${firebaseAuth.uid}").child(NODE_TASK).children) {
                        val task = i.getValue(Task::class.java)
                        if (task != null) list.add(task)
                    }
                    adapter.submitList(list)
                    Toast.makeText(
                        applicationContext,
                        "Данные загрузились!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        applicationContext,
                        "Ошибка в отправке!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}