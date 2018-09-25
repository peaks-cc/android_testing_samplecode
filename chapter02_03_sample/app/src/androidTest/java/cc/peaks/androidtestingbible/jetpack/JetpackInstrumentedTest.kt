package cc.peaks.androidtestingbible.jetpack

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import cc.peaks.androidtestingbible.R
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class JetpackInstrumentedTest {
  @Test
  fun gettingContextTest() {
    val context = InstrumentationRegistry.getTargetContext()
    val appName = context.getString(R.string.app_name)
    assertThat(appName).isEqualTo("AndroidTestingBible")
  }
}
