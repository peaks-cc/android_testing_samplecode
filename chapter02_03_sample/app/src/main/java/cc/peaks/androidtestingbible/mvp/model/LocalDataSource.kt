package cc.peaks.androidtestingbible.mvp.model

import cc.peaks.androidtestingbible.retrofit_okhttp.Repo
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class LocalDataSource {
  fun listRepos(user: String): Single<List<Repo>> {
    val repos = listOf(
        Repo(1, "repo 1", "Repository 1", false, null),
        Repo(2, "repo 2", "Repository 2", false, null)
    )
    return Single.just(repos).delay(1, TimeUnit.SECONDS)
  }
}