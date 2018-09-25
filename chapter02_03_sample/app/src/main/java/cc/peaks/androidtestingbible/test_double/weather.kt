package cc.peaks.androidtestingbible.test_double

enum class Weather {
  SUNNY, CLOUDY, RAINY
}

open class Satellite {
  open fun getWeather(latitude: Double, longitude: Double): Weather {
    /* 元の実装 */
    return Weather.SUNNY
  }
}

class StubSatellite(val anyWeather: Weather) : Satellite() {
  override fun getWeather(latitude: Double, longitude: Double): Weather {
    return anyWeather
  }
}

open class WeatherRecorder {
  open fun record(record: Record) {
    /* 元の実装 */
  }
}

data class Record(val description: String)

class MockWeatherRecorder : WeatherRecorder() {
  var record: Record? = null
  var isCalled = false

  override fun record(record: Record) {
    this.record = record
    isCalled = true
  }
}

open class WeatherFormatter {
  open fun format(weather: Weather): String = "Weather is ${weather}"
}

class SpyWeatherFormatter : WeatherFormatter() {
  var weather: Weather? = null
  var isCalled = false

  override fun format(weather: Weather): String {
    this.weather = weather
    isCalled = true
    return super.format(weather)
  }
}

class WeatherForecast(val satellite: Satellite,
                      val recorder: WeatherRecorder,
                      val formatter: WeatherFormatter) {
  fun shouldBringUmbrella(latitude: Double, longitude: Double): Boolean {
    val weather = satellite.getWeather(latitude, longitude)
    return when (weather) {
      Weather.SUNNY, Weather.CLOUDY -> false
      Weather.RAINY -> true
    }
  }

  fun recordCurrentWeather(latitude: Double, longitude: Double) {
    val weather = satellite.getWeather(latitude, longitude)
    val description = formatter.format(weather)
    recorder.record(Record(description))
  }
}