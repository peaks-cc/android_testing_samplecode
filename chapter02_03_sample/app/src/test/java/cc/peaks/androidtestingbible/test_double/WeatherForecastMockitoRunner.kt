package cc.peaks.androidtestingbible.test_double

import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class WeatherForecastMockitoRunner {
  @Mock
  lateinit var satellite: Satellite
  @Mock
  lateinit var recorder: WeatherRecorder
  @Spy
  val formatter: WeatherFormatter = WeatherFormatter()

  lateinit var weatherForecast: WeatherForecast

  @Before
  fun setUp() {
    whenever(satellite.getWeather(any(), any())).thenReturn(Weather.SUNNY)
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

    verify(recorder).record(any())
    argumentCaptor<Record>().apply {
      verify(recorder).record(capture())
      assertThat(firstValue.description).isEqualTo("Weather is SUNNY")
    }
  }

  @Test
  fun recordCurrentWeather_assertFormatterCalled() {
    weatherForecast.recordCurrentWeather(37.580006, -122.345106)
    verify(formatter).format(eq(Weather.SUNNY))
  }
}