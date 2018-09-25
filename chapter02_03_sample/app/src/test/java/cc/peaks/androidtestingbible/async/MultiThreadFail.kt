package cc.peaks.androidtestingbible.async

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.atomic.AtomicReference

class MultiThreadFail : TestRule {
  val errorRef = AtomicReference<AssertionError>()

  fun fail(message: String) {
    errorRef.set(AssertionError(message))
  }

  override fun apply(base: Statement?, description: Description?): Statement {
    return object : Statement() {
      @Throws(Throwable::class)
      override fun evaluate() {
        errorRef.set(null)
        base?.evaluate()
        errorRef.get()?.let { throw it }
      }
    }
  }
}