// Generated by view binder compiler. Do not edit!
package com.example.diplom.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.diplom.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class WeatherTodayHourBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView hourWeather;

  @NonNull
  public final ImageView imgWeatherHour;

  @NonNull
  public final TextView textTempHour;

  private WeatherTodayHourBinding(@NonNull ConstraintLayout rootView, @NonNull TextView hourWeather,
      @NonNull ImageView imgWeatherHour, @NonNull TextView textTempHour) {
    this.rootView = rootView;
    this.hourWeather = hourWeather;
    this.imgWeatherHour = imgWeatherHour;
    this.textTempHour = textTempHour;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static WeatherTodayHourBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static WeatherTodayHourBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.weather_today_hour, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static WeatherTodayHourBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.hour_weather;
      TextView hourWeather = ViewBindings.findChildViewById(rootView, id);
      if (hourWeather == null) {
        break missingId;
      }

      id = R.id.img_weather_hour;
      ImageView imgWeatherHour = ViewBindings.findChildViewById(rootView, id);
      if (imgWeatherHour == null) {
        break missingId;
      }

      id = R.id.text_temp_hour;
      TextView textTempHour = ViewBindings.findChildViewById(rootView, id);
      if (textTempHour == null) {
        break missingId;
      }

      return new WeatherTodayHourBinding((ConstraintLayout) rootView, hourWeather, imgWeatherHour,
          textTempHour);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
