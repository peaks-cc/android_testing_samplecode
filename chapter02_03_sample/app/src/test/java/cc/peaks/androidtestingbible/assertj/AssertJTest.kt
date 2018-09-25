package cc.peaks.androidtestingbible.assertj

import org.assertj.core.api.Assertions.*
import org.assertj.core.api.SoftAssertions
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class AssertJTest {
  @Before
  fun setUp() {
  }

  @After
  fun tearDown() {
  }

  @Test
  fun stringAssertion() {
    assertThat("TOKYO")
        .`as`("TEXT CHECK TOKYO")
        .isEqualTo("TOKYO")
        .isEqualToIgnoringCase("tokyo")
        .isNotEqualTo("KYOTO")
        .isNotBlank()
        .startsWith("TO")
        .endsWith("YO")
        .contains("OKY")
        .matches("[A-Z]{5}")
        .isInstanceOf(String::class.java)
  }

  @Ignore("Skip SoftAssertions")
  @Test
  fun softAssertion() {
    SoftAssertions().apply {
      assertThat("TOKYO")
          .`as`("TEXT CHECK TOKYO")
          .isEqualTo("HOKKAIDO")
          .isEqualToIgnoringCase("tokyo")
          .isNotEqualTo("KYOTO")
          .isNotBlank()
          .startsWith("TO")
          .endsWith("YO")
          .contains("OKY")
          .matches("[A-Z]{5}")
          .isInstanceOf(String::class.java)
    }.assertAll()
  }

  @Test
  fun numberAssertion() {
    assertThat(3.14159)
        .isNotZero()
        .isNotNegative()
        .isGreaterThan(3.0)
        .isLessThanOrEqualTo(4.0)
        .isBetween(3.0, 3.2)
        .isCloseTo(Math.PI, within(0.001))
  }

  @Test
  fun collectionAssertion() {
    val target = listOf("Giants", "Dodgers", "Athletics")
    assertThat(target)
        .hasSize(3)
        .contains("Dodgers")
        .containsOnly("Athletics", "Dodgers", "Giants")
        .containsExactly("Giants", "Dodgers", "Athletics")
        .doesNotContain("Padres")
  }

  @Test
  fun filterAndTuple() {
    val target = listOf(
        BallTeam("Giants", "San Francisco", "AT&T Park"),
        BallTeam("Dodgers", "Los Angels", "Dodger Stadium"),
        BallTeam("Angels", "Los Angels", "Angel Stadium"),
        BallTeam("Athletics", "Oakland", "Oakland Coliseum"),
        BallTeam("Padres", "San Diego", "Petco Park")

    )
    assertThat(target)
        .filteredOn { team -> team.city.startsWith("San") }
        .filteredOn { team -> team.city.endsWith("Francisco") }
        .extracting("name", String::class.java)
        .containsExactly("Giants")
    assertThat(target)
        .filteredOn { team -> team.city == "Los Angels" }
        .extracting("name", "stadium")
        .containsExactly(
            tuple("Dodgers", "Dodger Stadium"),
            tuple("Angels", "Angel Stadium")
        )
  }

  @Test
  fun fluentExceptionHandling() {
    assertThatExceptionOfType(RuntimeException::class.java)
        .isThrownBy { functionMayThrow() }
        .withMessage("Aborted!")
        .withNoCause()
  }

  fun functionMayThrow(): Nothing = throw RuntimeException("Aborted!")

}