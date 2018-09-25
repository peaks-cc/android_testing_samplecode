package cc.peaks.androidtestingbible.mvp.model

import cc.peaks.androidtestingbible.retrofit_okhttp.Repo
import io.reactivex.Single

class GitHubRepository(val localDataSource: LocalDataSource) {
  fun listRepos(user: String): Single<List<Repo>> {
    return localDataSource.listRepos(user)
  }
}