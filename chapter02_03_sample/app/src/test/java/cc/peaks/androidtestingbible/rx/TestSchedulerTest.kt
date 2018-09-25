package cc.peaks.androidtestingbible.rx

import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import java.util.concurrent.TimeUnit


class TestSchedulerTest {

  @Test
  fun scheduler() {
    val scheduler = TestScheduler()
    val ticker: Observable<Long> = Observable
        .interval(1, TimeUnit.SECONDS, scheduler)
    val subscriber = ticker.take(3).test()
    scheduler.advanceTimeBy(3, TimeUnit.SECONDS)
    subscriber.await().assertValues(0, 1, 2)
  }

}