package cc.peaks.androidtestingbible.mvp.presenter

import cc.peaks.androidtestingbible.mvp.model.GitHubRepository
import cc.peaks.androidtestingbible.mvp.view.Presenter
import cc.peaks.androidtestingbible.mvp.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


class ListPresenter(val view: View,
                    val repository: GitHubRepository) : Presenter {
  override fun getRepositoryList(name: String) {
    repository.listRepos(name)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy { view.showRepositoryList(it) }
  }
}