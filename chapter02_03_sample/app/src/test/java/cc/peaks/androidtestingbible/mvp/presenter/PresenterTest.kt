package cc.peaks.androidtestingbible.mvp.presenter

import cc.peaks.androidtestingbible.mvp.model.GitHubRepository
import cc.peaks.androidtestingbible.mvp.view.View
import cc.peaks.androidtestingbible.retrofit_okhttp.Repo
import cc.peaks.androidtestingbible.rx.RxImmediateSchedulerRule
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test

class PresenterTest {
  val view: View = mock()
  val repository: GitHubRepository = mock {
    on { listRepos(any()) } doReturn Single.just(listOf(
        Repo(), Repo(), Repo()
    ))
  }
  val presenter = ListPresenter(view, repository)

  @get:Rule
  val rule = RxImmediateSchedulerRule()

  @Test
  fun getRepositoryList_assertViewPresenterInteraction() {
    presenter.getRepositoryList("shiroyama")
    argumentCaptor<List<Repo>>().apply {
      verify(view, times(1)).showRepositoryList(capture())
      assertThat(firstValue).hasSize(3)
    }
  }
}