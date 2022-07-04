package com.example.diplom
//импортируем библиотеки
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.databinding.WeatherTommorwBinding
import com.squareup.picasso.Picasso
//класс наслудующий и принимающие переменные их weather_tomorrow.xml
class WeatherTomorrowAdapter :
    RecyclerView.Adapter<WeatherTomorrowAdapter.WeatherTomorrowAdapterViewHolder>() {

    val weatherTom = ArrayList<WeatherTomorrow>()
//передает контекст родителю
    class WeatherTomorrowAdapterViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = WeatherTommorwBinding.bind(item)
        fun bind(weatherTomorrow: WeatherTomorrow) = with(binding) {
            weatherTextTommorw.text = weatherTomorrow.data
            weatherTempTommorw.text = weatherTomorrow.temp + NODE_TEMP
            Picasso.get()
                .load(NODE_HTTP + weatherTomorrow.icon)
                .into(weatherImgTommorw)
        }
    }
//создает компоненты
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherTomorrowAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_tommorw, parent, false)
        return  WeatherTomorrowAdapterViewHolder(view)
    }
//отрисовывает по позициям
    override fun onBindViewHolder(holder: WeatherTomorrowAdapterViewHolder, position: Int) {
        holder.bind(weatherTom[position])
    }
//получет кол-во отрисовываемых элементов
    override fun getItemCount(): Int {
        return weatherTom.size
    }
//добавляет новые элементы
    fun addTommorw(weatherTomorrow: WeatherTomorrow) {
        weatherTom.add(weatherTomorrow)
        notifyDataSetChanged()
    }
//удаляет
    fun onDelete() {
        weatherTom.clear()
    }
}