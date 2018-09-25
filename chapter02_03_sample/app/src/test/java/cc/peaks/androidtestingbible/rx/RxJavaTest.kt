package cc.peaks.androidtestingbible.rx

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.concurrent.TimeUnit


class RxJavaTest {
  val observable = Observable.just("Rx").delay(1, TimeUnit.SECONDS)

  val errorObservable: Observable<RuntimeException> = Observable.error(RuntimeException("Oops!"))

  val listObservable: Observable<String> = listOf("Giants", "Dodgers", "Athletics")
      .toObservable()
      .delay(1, TimeUnit.SECONDS)

  @Test
  fun observableTestNoGood() {
    observable.subscribeBy(
        onNext = {
          System.out.println(it)
          assertThat(it).isEqualTo("Rx")
        }
    )
  }

  @Test
  fun observableTestSuccess() {
    val subscriber = observable.test()
    subscriber.await()
        .assertComplete()
        .assertValue("Rx")

    errorObservable.test()
        .await()
        .assertNotComplete()
        .assertError(RuntimeException::class.java)
        .assertErrorMessage("Oops!")

    val strings: List<String> = listObservable
        .test()
        .await()
        .assertComplete()
        .assertValues("Giants", "Dodgers", "Athletics")
        .values()

    assertThat(strings)
        .isNotEmpty()
        .containsExactly("Giants", "Dodgers", "Athletics")
  }

}