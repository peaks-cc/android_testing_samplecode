package cc.peaks.androidtestingbible.mvp.model

import cc.peaks.androidtestingbible.retrofit_okhttp.Repo
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.stubbing.Answer

class GitHubRepositoryTest {
  val localDataSource = mock<LocalDataSource>(
      defaultAnswer = Answer { throw RuntimeException() }
  )
  lateinit var gitHubRepository: GitHubRepository

  @Before
  fun setUp() {
    doReturn(Single.just(listOf(Repo(), Repo(), Repo())))
        .whenever(localDataSource).listRepos(any())
    gitHubRepository = GitHubRepository(localDataSource)
  }

  @Test
  fun listRepos() {
    val list = gitHubRepository.listRepos("shiroyama")
        .test()
        .assertComplete()
        .assertNoErrors()
        .values()[0]
    assertThat(list).hasSize(3)

    verify(localDataSource, times(1)).listRepos(any())
    verifyNoMoreInteractions(localDataSource)
  }
}