package cc.peaks.androidtestingbible.test_double

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class WeatherForecastTest {
  lateinit var weatherForecast: WeatherForecast
  lateinit var recorder: MockWeatherRecorder
  lateinit var formatter: SpyWeatherFormatter

  @Before
  fun setUp() {
    val satellite = StubSatellite(Weather.SUNNY)
    recorder = MockWeatherRecorder()
    formatter = SpyWeatherFormatter()
    weatherForecast = WeatherForecast(satellite, recorder, formatter)
  }

  @Test
  fun shouldBringUmbrella_givenSunny_returnsFalse() {
    val actual = weatherForecast.shouldBringUmbrella(37.580006, -122.345106)
    assertThat(actual).isFalse()
  }

  @Test
  fun recordCurrentWeather_assertCalled() {
    weatherForecast.recordCurrentWeather(37.580006, -122.345106)

    val isCalled = recorder.isCalled
    assertThat(isCalled).isTrue()

    val record = recorder.record
    assertThat(record?.description).isEqualTo("Weather is SUNNY")
  }

  @Test
  fun recordCurrentWeather_assertFormatterCalled() {
    weatherForecast.recordCurrentWeather(37.580006, -122.345106)

    val isCalled = formatter.isCalled
    assertThat(isCalled).isTrue()

    val weather = formatter.weather
    assertThat(weather)
        .isIn(Weather.SUNNY, Weather.CLOUDY, Weather.RAINY)
  }
}