package cc.peaks.androidtestingbible.retrofit_okhttp

import io.reactivex.Single

class GitHubRemoteDataSource(val gitHubService: GitHubService) {
  fun listRepos(user: String): Single<List<Repo>> {
    return gitHubService.listRepos(user)
  }
}