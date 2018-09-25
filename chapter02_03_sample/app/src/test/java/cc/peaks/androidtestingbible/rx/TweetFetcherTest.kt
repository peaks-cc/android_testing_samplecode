package cc.peaks.androidtestingbible.rx

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test


class TweetFetcherTest {
  lateinit var repository: TweetRepository
  lateinit var tweetFetcher: TweetFetcher

  @Before
  fun setUp() {
    repository = mock(name = "MockTweetRepository")
    val tweets = listOf(Tweet("hello", 1), Tweet("from", 2), Tweet("world", 3))
    whenever(repository.recents()).thenReturn(Single.just(tweets))

    tweetFetcher = TweetFetcher(
        Schedulers.trampoline(),
        Schedulers.trampoline(),
        repository
    )
  }

  @Test
  fun tweetRepositoryTest() {
    val list = repository.recents()
        .test()
        .await()
        .values()[0]
    assertThat(list)
        .extracting("tweet", String::class.java)
        .containsExactly("hello", "from", "world")
  }

  @Test
  fun tweetFetcherTest() {
    tweetFetcher.recents(
        onSuccess = {
          System.out.println("onSuccess")
          assertThat(it)
              .extracting("tweet", String::class.java)
              .containsExactly("hello", "from", "world")
        },
        onError = {})
  }

}