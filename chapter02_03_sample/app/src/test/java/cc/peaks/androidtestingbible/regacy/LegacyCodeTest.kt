package cc.peaks.androidtestingbible.regacy

import androidx.test.InstrumentationRegistry
import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LegacyCodeTest {
  private lateinit var callback: LegacyCode.Callback<NewData>

  @Mock(name = "localDataFetcher")
  lateinit var localdatafetcher: LocalDataFetcher
  @Mock(name = "remoteDataFetcher")
  lateinit var remoteDataFetcher: RemoteDataFetcher
  @Mock(name = "networkUtils")
  lateinit var networkUtilsWrapper: NetworkUtilsWrapper
  @Spy
  val converter = DataConverter()

  @InjectMocks
  lateinit var legacyCode: LegacyCode

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    whenever(localdatafetcher.fetch(any())).thenAnswer { invocation ->
      val param = invocation.arguments[0] as String
      return@thenAnswer OldData("local:$param")
    }
    whenever(remoteDataFetcher.fetch(any())).thenAnswer { invocation ->
      val param = invocation.arguments[0] as String
      return@thenAnswer OldData("remote:$param")
    }
    whenever(networkUtilsWrapper.isOnline(any())).thenReturn(true)

    callback = mock()
  }

  @Test
  fun loadData() {
    val context = InstrumentationRegistry.getTargetContext()
    legacyCode.loadData("foo", context, callback)

    argumentCaptor<NewData>().apply {
      verify(callback, times(1)).onSuccess(capture())
      verify(converter).convert(any())
      assertThat(firstValue.data).isEqualTo("converted:remote:foo")
    }
  }
}