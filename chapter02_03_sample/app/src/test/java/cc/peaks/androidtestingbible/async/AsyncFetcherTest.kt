package cc.peaks.androidtestingbible.async

import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch

class AsyncFetcherTest {

  lateinit var fetcher: StringFetcher
  lateinit var asyncFetcher: AsyncStringFetcher
  lateinit var latch: CountDownLatch

  val onSuccess: (value: String) -> Unit = { _ -> rule.fail("ERROR") }
  val onFailue: (error: Throwable) -> Unit = { _ -> rule.fail("ERROR") }

  @get:Rule
  val rule = MultiThreadFail()

  @Before
  fun setUp() {
    fetcher = spy()
    asyncFetcher = AsyncStringFetcher(fetcher)
    latch = CountDownLatch(1)
  }

  @Test
  fun fetchAsync_expectFailButPass_01() {
    asyncFetcher.fetchAsync(
        { _ -> fail("onSuccess") },
        { _ -> fail("onFailure") }
    )
  }

  @Test
  fun fetchAsync_expectFailButPass_02() {
    asyncFetcher.fetchAsync(
        { _ -> fail("onSuccess") },
        { _ -> fail("onFailure") }
    )
    Thread.sleep(2000L)
  }

  @Ignore
  @Test(expected = RuntimeException::class)
  fun fetchAsync_expectPassButFail() {
    asyncFetcher.fetchAsync(
        { _ -> throw RuntimeException("onSuccess") },
        { _ -> throw RuntimeException("onFailure") }
    )
    Thread.sleep(2000L)
  }

  @Test
  fun fetchAsync_callbackedProperly() {
    asyncFetcher.fetchAsync(
        { value ->
          assertThat(value).isEqualTo("foo")
          verify(fetcher, times(1)).fetch()
          System.out.println("success")
          latch.countDown()
        },
        onFailue
    )
    System.out.println("fetchAsync invoked.")
    latch.await()
  }

  @Test
  fun fetchAsync_callbackedProperly_failure() {
    doThrow(RuntimeException("ERROR")).whenever(fetcher).fetch()
    asyncFetcher.fetchAsync(
        onSuccess,
        { error ->
          assertThat(error)
              .isInstanceOf(RuntimeException::class.java)
              .hasMessageContaining("ERROR")
          verify(fetcher, times(1)).fetch()
          System.out.println("failure")
          latch.countDown()
        }
    )
    System.out.println("fetchAsync invoked.")
    latch.await()
  }

  @Test
  fun fetchAsync_future_OK() {
    var actualValue: String? = null
    var actualError: Throwable? = null
    asyncFetcher.fetchAsync(
        { value -> actualValue = value },
        { error -> actualError = error }
    ).get()
    verify(fetcher, times(1)).fetch()
    assertThat(actualValue).isEqualTo("foo")
    assertThat(actualError).isNull()
  }

  @Test
  fun fetchAsync_future_NG() {
    doThrow(RuntimeException("ERROR")).whenever(fetcher).fetch()
    var actualValue: String? = null
    var actualError: Throwable? = null
    asyncFetcher.fetchAsync(
        { value -> actualValue = value },
        { error -> actualError = error }
    ).get()
    verify(fetcher, times(1)).fetch()
    assertThat(actualValue).isNull()
    assertThat(actualError)
        .isExactlyInstanceOf(RuntimeException::class.java)
        .hasMessage("ERROR")
  }

  @Test
  fun fetchAsync_with_CurrentThreadExecutorService() {
    val executor = CurrentThreadExecutorService()
    asyncFetcher = AsyncStringFetcher(fetcher, executor)

    var actualValue: String? = null
    var actualError: Throwable? = null
    asyncFetcher.fetchAsync(
        { value -> actualValue = value },
        { error -> actualError = error }
    ).get()
    verify(fetcher, times(1)).fetch()
    assertThat(actualValue).isEqualTo("foo")
    assertThat(actualError).isNull()
  }

}