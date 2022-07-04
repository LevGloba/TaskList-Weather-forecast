package com.example.diplom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.databinding.WeatherTodayHourBinding
import com.squareup.picasso.Picasso
//класс наслудующий и принимающие переменные их weather_tomorrow.x
class WeatherHourAdapter :
    RecyclerView.Adapter<WeatherHourAdapter.WeatherHourAdapterViewHolder>() {

    val weatherHR = ArrayList<WeatherHour>()
    //передает контекст родителю
    class WeatherHourAdapterViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = WeatherTodayHourBinding.bind(item)
        fun bind(weatherHour: WeatherHour) = with(binding) {
            val regex = Regex("\\s\\d{2}")
            val regexTwo = Regex("\\d{2}\$")
            val hour = regex.find(weatherHour.hour)!!.value
            hourWeather.text = regexTwo.find(hour)!!.value
            Picasso.get()
                .load(NODE_HTTP + weatherHour.icon)
                .into(imgWeatherHour)
            textTempHour.text = weatherHour.temp + NODE_TEMP
        }
    }
    //создает компоненты
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherHourAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_today_hour, parent, false)
        return  WeatherHourAdapterViewHolder(view)
    }
    //отрисовывает по позиция
    override fun onBindViewHolder(holder: WeatherHourAdapterViewHolder, position: Int) {
        holder.bind(weatherHR[position])
    }
    //получет кол-во отрисовываемых элементов
    override fun getItemCount(): Int {
        return weatherHR.size
    }
    //добавляет новые элементы
    fun addTommorw(weatherHour: WeatherHour) {
        weatherHR.add(weatherHour)
        notifyDataSetChanged()
    }
    //удаляет
    fun onDelete() {
        weatherHR.clear()
    }
}