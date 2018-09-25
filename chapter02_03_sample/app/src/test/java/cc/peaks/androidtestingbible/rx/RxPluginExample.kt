package cc.peaks.androidtestingbible.rx

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.rxkotlin.subscribeBy
import org.assertj.core.api.Assertions.assertThat
import org.junit.BeforeClass
import org.junit.Test
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit


class RxPluginExample {
  val observable = Observable.just("Rx").delay(1, TimeUnit.SECONDS)

  companion object {
    @BeforeClass
    @JvmStatic
    fun setUpClass() {
      System.out.println("setUpClass")
      val immediate = object : Scheduler() {
        override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
          return super.scheduleDirect(run, 0, unit)
        }

        override fun createWorker(): Worker {
          return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
        }
      }

      RxJavaPlugins.setInitIoSchedulerHandler { _ -> immediate }
      RxJavaPlugins.setInitComputationSchedulerHandler { _ -> immediate }
      RxJavaPlugins.setInitNewThreadSchedulerHandler { _ -> immediate }
      RxJavaPlugins.setInitSingleSchedulerHandler { _ -> immediate }
      RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> immediate }
    }
  }

  @Test
  fun rxPluginExample() {
    observable.subscribeBy(
        onNext = {
          System.out.println(it)
          assertThat(it).isEqualTo("Rx")
        }
    )
  }
}