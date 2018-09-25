package cc.peaks.androidtestingbible.room

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryLocalDataSourceAndroidTest {
  lateinit var repositoryLocalDataSource: RepositoryLocalDataSource

  @Before
  fun setUp() {
    val context = InstrumentationRegistry.getTargetContext()
    val db = Room
        .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
        .allowMainThreadQueries() // just for test
        .build()
    repositoryLocalDataSource = RepositoryLocalDataSource(db)
  }

  @Test
  fun insertAll() {
    var list = repositoryLocalDataSource.findByOwner("shiroyama")
    assertThat(list).isEmpty()

    var id = 0L
    val owner = "shiroyama"
    repositoryLocalDataSource.insertAll(
        Repository(++id, "hello", "hello", owner),
        Repository(++id, "from", "from", owner),
        Repository(++id, "world", "world", owner)
    )

    list = repositoryLocalDataSource.findByOwner("shiroyama")
    assertThat(list).hasSize(3)
  }

  @Test
  fun insertAll2() {
    var list = repositoryLocalDataSource.findByOwner("shiroyama")
    assertThat(list).isEmpty()

    var id = 0L
    val owner = "shiroyama"
    repositoryLocalDataSource.insertAll(
        Repository(++id, "hello", "hello", owner),
        Repository(++id, "from", "from", owner),
        Repository(++id, "world", "world", owner)
    )

    list = repositoryLocalDataSource.findByOwner("shiroyama")
    assertThat(list).hasSize(3)
  }
}