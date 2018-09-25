package cc.peaks.androidtestingbible.input_checker

import org.assertj.core.api.Assertions
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class InputCheckerTest {
  lateinit var target: InputChecker

  @Before
  fun setUp() {
    target = InputChecker()
  }

  @After
  fun tearDown() {
  }

  @Test
  fun isValid_givenLessThan3_returnsFalse() {
    val actual = target.isValid("ab")
    assertThat(actual, `is`(false))
  }

  @Test
  fun isValid_givenAlphabetic_returnsTrue() {
    val actual = target.isValid("abc")
    assertThat(actual, `is`(true))
  }

  @Test
  fun isValid_givenNumeric_returnsTrue() {
    val actual = target.isValid("123")
    assertThat(actual, `is`(true))
  }

  @Test
  fun isValid_givenAlphaNumeric_returnsTrue() {
    val actual = target.isValid("abc123")
    assertThat(actual, `is`(true))
  }

  @Test
  fun isValid_givenInvalidCharacter_returnsFalse() {
    val actual = target.isValid("abc@123")
    assertThat(actual, `is`(false))
  }

  @Test(expected = IllegalArgumentException::class)
  fun isValid_givenNull_throwsIllegalArgumentException() {
    target.isValid("")
  }

  @Ignore("テスト対象が仮実装なので一時的にスキップ")
  @Test
  fun temporarilySkipThisTest() {
  }

  @Test
  fun isValid_givenAlphaNumeric_returnsTrue_withAssertJ() {
    val actual = target.isValid("abc123")
    Assertions.assertThat(actual).isTrue()
  }

}