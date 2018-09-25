package cc.peaks.androidtestingbible.test_double

import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Before
import org.junit.Test

class WeatherForecastMockito {
  lateinit var satellite: Satellite
  lateinit var recorder: WeatherRecorder
  lateinit var formatter: WeatherFormatter
  lateinit var weatherForecast: WeatherForecast

  @Before
  fun setUp() {
    satellite = mock(name = "MockSatellite")
    whenever(satellite.getWeather(any(), any())).thenReturn(Weather.CLOUDY)
    whenever(satellite.getWeather(eq(37.580006), eq(-122.345106))).thenReturn(Weather.SUNNY)
    whenever(satellite.getWeather(eq(37.792872), eq(-122.396915))).thenReturn(Weather.RAINY)
    recorder = mock(name = "MockRecorder")
    formatter = spy(WeatherFormatter())
    weatherForecast = WeatherForecast(satellite, recorder, formatter)
  }

  @Test
  fun shouldBringUmbrella_givenBurlingame_returnsTrue() {
    val actual = weatherForecast.shouldBringUmbrella(37.580006, -122.345106)
    assertThat(actual).isFalse()
  }

  @Test
  fun shouldBringUmbrella_givenInJapan_returnsFalse() {
    val actual = weatherForecast.shouldBringUmbrella(35.669784, 139.817728)
    assertThat(actual).isFalse()
  }

  @Test
  fun shouldBringUmbrella_thenAnswer() {
    whenever(satellite.getWeather(any(), any()))
        .thenAnswer { invocation ->
          val latitude = invocation.arguments[0] as Double
          val longitude = invocation.arguments[1] as Double

          if (latitude in 20.424086..45.550999 &&
              longitude in 122.933872..153.980789) {
            return@thenAnswer Weather.SUNNY
          } else {
            return@thenAnswer Weather.RAINY
          }
        }

    val actualBurlingame = weatherForecast.shouldBringUmbrella(37.580006, -122.345106)
    assertThat(actualBurlingame).isTrue()

    val actualJapan = weatherForecast.shouldBringUmbrella(35.669784, 139.817728)
    assertThat(actualJapan).isFalse()
  }

  @Test
  fun shouldBringUmbrella_expectExceptionHappens() {
    whenever(satellite.getWeather(any(), any()))
        .thenThrow(RuntimeException("ERROR"))

    assertThatExceptionOfType(RuntimeException::class.java)
        .isThrownBy {
          weatherForecast.shouldBringUmbrella(37.580006, -122.345106)
        }
        .withMessage("ERROR")
        .withNoCause()
  }

  @Test
  fun recordCurrentWeather_assertRecorderCalled() {
    weatherForecast.recordCurrentWeather(37.580006, -122.345106)
    argumentCaptor<Record>().apply {
      verify(recorder, times(1)).record(capture())
      assertThat(firstValue.description).isEqualTo("Weather is SUNNY")
    }
  }

  @Test
  fun recordCurrentWeather_assertFormatterCalled() {
    weatherForecast.recordCurrentWeather(37.580006, -122.345106)

    argumentCaptor<Weather>().apply {
      verify(formatter, times(1)).format(capture())
      assertThat(firstValue).isEqualTo(Weather.SUNNY)
    }
  }

  @Test(expected = IndexOutOfBoundsException::class)
  fun listSpyBadExample() {
    val list: List<String> = spy(arrayListOf())
    whenever(list[any()]).thenReturn("HELLO")
    doReturn("HELLO").whenever(list)[any()]

    assertThat(list[0]).isEqualTo("HELLO")
  }

  @Test
  fun listSpyOkExample() {
    val list: List<String> = spy(arrayListOf())
    doAnswer { invocation ->
      val index = invocation.arguments[0] as Int
      return@doAnswer if (index == 0) "HELLO" else "WORLD"
    }.whenever(list)[any()]

    assertThat(list[0]).isEqualTo("HELLO")
    assertThat(list[1]).isEqualTo("WORLD")
  }

}