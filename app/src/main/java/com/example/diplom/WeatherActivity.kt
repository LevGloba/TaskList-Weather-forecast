package com.example.diplom

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.diplom.databinding.WeatherBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject


class WeatherActivity: BaseActivity() {

    //инициализация локальных переменных
    private lateinit var binding: WeatherBinding
    lateinit var adapter: WeatherTomorrowAdapter
    lateinit var adapter_Hour: WeatherHourAdapter
    val regexTemp = "^\\d\\d?".toRegex()
    val regexDate = "-".toRegex()
    val regexCor = "\\d{2}.\\d{2}".toRegex()
    val regNameCityOne = "^\\w+".toRegex()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WeatherBinding.inflate(layoutInflater). also { setContentView(it.root) }


//вызов посредника базы данных
        initFirebase()
        getPosition()
        //кнопка с иконкой блокнот вызывает функции
        binding.taskList.setOnClickListener {
            onMoveTask() }

        //Кнопка с иконкой лупа
        binding.imageSearch.setOnClickListener {
//вызывает AlertDialog
            val mDialogView = layoutInflater.inflate(R.layout.search_weather, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Задача")
            val mAlertDialog = mBuilder.show()
            mDialogView.findViewById<ImageButton>(R.id.buttonPosistiv).setOnClickListener {
                val editSearchWeatherAlert: String = mDialogView.findViewById<EditText>(R.id.editSearchWeatherAlert).text.toString()
                if (editSearchWeatherAlert != "") {
                    mAlertDialog.dismiss()
                    //вызывает функцию для получения данных прогноза погоды
                    onSearchWeather(editSearchWeatherAlert)
                    adapter.onDelete()
                    adapter_Hour.onDelete()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Поле пустое!\nПожалуйста заполните поле.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
//вызывает функцию выхода из аккаунта пользователя
        binding.ImgExitButton.setOnClickListener {
            onExit()
        }
        //вызывает функцию по отрисовки элементов
        initRcView()
    }


    fun getPosition() {

        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 123)


        val g = GPStracker(applicationContext) //создаём трекер
        val l = g.getLocation() // получаем координаты

        if (l != null) {
            val lat = regexCor.find(l.latitude.toString())!!.value // широта
            val lon = regexCor.find(l.longitude.toString())!!.value // долгота
            Toast.makeText(applicationContext, "Широта: $lat\nДолгота: $lon", Toast.LENGTH_LONG)
                .show() // вывод в тосте
            val name = "$lat $lon"
            onSearchWeather(name)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                getPosition()
            }, 6000)
        }
    }

//  отрисовывает элементы списка погоды по часам и по дням
    private fun initRcView() = with(binding) {
        adapter = WeatherTomorrowAdapter()
        adapter_Hour = WeatherHourAdapter()
        rcViewWeatherTooMoroow.layoutManager = LinearLayoutManager(this@WeatherActivity)
        rcViewWeatherTooMoroow.adapter = adapter
        rcViewWeatherToday.layoutManager = LinearLayoutManager(this@WeatherActivity, LinearLayoutManager.HORIZONTAL, false)
        rcViewWeatherToday.adapter = adapter_Hour
    }

    // выходит из аккаунта и завершает работу класса
    fun onExit(){
        firebaseAuth.signOut()
            finish()
    }

    // получает данные со сервера "weatherapi"
    private fun onSearchWeather(name: String) {
        val url = "http://api.weatherapi.com/v1/forecast.json?key=$API_KEY&q=$name&days=10&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET,
            url,
            {
                //получения данных из объекта и их сохранение в классе
                response ->
                val obj = JSONObject(response)

                val tz_idCity = obj.getJSONObject("location").getString("region")
                    var nameCity = regNameCityOne.find(tz_idCity)!!.value
                val tempNow = obj.getJSONObject("current")
                val iconNow = tempNow.getJSONObject("condition")
                val weather = WeatherUser(
                    nameCity,
                    regexTemp.find(tempNow.getString("temp_c"))!!.value,
                    iconNow.getString("icon"))

                val forecastObj = obj.getJSONObject("forecast")
                val forecastO = forecastObj.getJSONArray("forecastday").getJSONObject(0)

                val forecastday = obj.getJSONObject("forecast").getJSONArray("forecastday")
                val hourArray = forecastO.getJSONArray("hour")

                for(i in 0 until forecastday.length()) {
                    val nextDay = forecastday.getJSONObject(i)
                    val dataDay = nextDay.getString("date")
                    val tempDay = nextDay.getJSONObject("day").getString("avgtemp_c")
                    val iconDay = nextDay.getJSONObject("day").getJSONObject("condition").getString("icon")
                    val weatherTomorrow = WeatherTomorrow(
                        dataDay.replace(regexDate, ".") ,
                        regexTemp.find(tempDay)!!.value,
                        iconDay)
                    adapter.addTommorw(weatherTomorrow)
                }
                for (i in 0 until hourArray.length()) {
                    val hourObj = hourArray.getJSONObject(i)
                    val timeHour = hourObj.getString("time")
                    val tempHour = hourObj.getString("temp_c")
                    val iconHour = hourObj.getJSONObject("condition").getString("icon")
                    val weatherHour = WeatherHour(timeHour,iconHour, regexTemp.find(tempHour)!!.value)
                    adapter_Hour.addTommorw(weatherHour)
                }

                Shows_weather_information(weather)
                Log.d("MyLog", "Response: ${tempNow.getString("temp_c")}\n${iconNow.getString("icon")}")
            },
            {
                Log.d("MyLog", "VolleyError: $it" )
                Toast.makeText(
                    applicationContext,
                    "Не удалось загрузить данные, по пробуйте снова.\n$it",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
        queue.add(stringRequest)
    }

    // отображает прогноз погоды на данный момент
    fun Shows_weather_information(weather: WeatherUser){
        with(binding) {
            weatherViewMain.visibility = View.VISIBLE
            cityTextViewMain.text = weather.location
            tempCityMain.text = "${weather.temp}$NODE_TEMP"
            todayText.visibility = View.VISIBLE
            Picasso.get()
                .load(NODE_HTTP + weather.icon)
                .error(R.drawable.ic_cloud)
                .into(todayIcon)
            rcViewWeatherTooMoroow.visibility = View.VISIBLE
        }
    }

    // вызывает "MainActivity и завершает работу класса"
    private fun onMoveTask() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}