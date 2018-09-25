package cc.peaks.androidtestingbible.room

import android.arch.persistence.room.Room
import androidx.test.InstrumentationRegistry
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(Enclosed::class)
class EnclosedTest {
  abstract class DBTest {
    lateinit var repositoryLocalDataSource: RepositoryLocalDataSource

    @Before
    fun setUpParent() {
      val context = InstrumentationRegistry.getTargetContext()
      val db = Room
          .databaseBuilder(context, AppDatabase::class.java, "repository-db")
          .allowMainThreadQueries() // just for test
          .build()
      repositoryLocalDataSource = RepositoryLocalDataSource(db)

      System.out.println("DBTest#setUp")
    }

    @After
    fun tearDownParent() {
      System.out.println("DBTest#tearDown")
    }
  }

  @RunWith(RobolectricTestRunner::class)
  class BlankRecord : DBTest() {
    @Before
    fun setUp() {
      System.out.println("BlankRecord#setUp")
    }

    @After
    fun tearDown() {
      System.out.println("BlankRecord#tearDown")
    }

    @Test
    fun insertAll_successfully_persist_record() {
      var shiroyamaOwners = repositoryLocalDataSource.findByOwner("shiroyama")
      assertThat(shiroyamaOwners).isEmpty()

      repositoryLocalDataSource.insertAll(Repository(0, "hello", "hello", "shiroyama"))

      shiroyamaOwners = repositoryLocalDataSource.findByOwner("shiroyama")
      assertThat(shiroyamaOwners).hasSize(1)
    }
  }

  @RunWith(RobolectricTestRunner::class)
  class RecordPrepared : DBTest() {
    @Before
    fun setUp() {
      System.out.println("BlankRecord#setUp")

      val shiroyama = "shiroyama"
      val yamazaki = "yamazaki"
      repositoryLocalDataSource.insertAll(
          Repository(1, "hello", "hello", shiroyama),
          Repository(2, "world", "world", shiroyama),
          Repository(3, "yay!", "yay!", yamazaki)
      )
    }

    @After
    fun tearDown() {
      System.out.println("BlankRecord#tearDown")
    }

    @Test
    fun findByOwner_givenShiroyama_returnsSizeCount3() {
      val shiroyamaOwners = repositoryLocalDataSource.findByOwner("shiroyama")
      assertThat(shiroyamaOwners).hasSize(2)
    }

    @Test
    fun findByOwner_givenYamazaki_returnsSizeCount1() {
      val yamazakiOwners = repositoryLocalDataSource.findByOwner("yamazaki")
      assertThat(yamazakiOwners).hasSize(1)
    }
  }
}