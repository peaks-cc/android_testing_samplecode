package cc.peaks.androidtestingbible.test_double

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test

class SpyStub {
  class OtherThingFetcher {
    fun fetchOtherThing(): String {
      return ""
    }
  }

  class OtherThingHandler {
    fun doWithOtherThing(text: String) {
      /* nop */
    }
  }

  class TargetClass(val fetcher: OtherThingFetcher,
                    val handler: OtherThingHandler) {
    fun doSomething() {
      val text = fetcher.fetchOtherThing()
      handler.doWithOtherThing(text)
    }
  }

  @Test
  fun confirm_doOtherThing_called() {
    val fetcher: OtherThingFetcher = mock()
    whenever(fetcher.fetchOtherThing()).thenReturn("OTHER")

    val handler: OtherThingHandler = mock()
    val targetClass = TargetClass(fetcher, handler)

    targetClass.doSomething()
    verify(handler).doWithOtherThing(eq("OTHER"))
  }
}