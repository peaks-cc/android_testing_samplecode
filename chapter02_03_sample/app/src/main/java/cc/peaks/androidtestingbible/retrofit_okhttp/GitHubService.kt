package cc.peaks.androidtestingbible.retrofit_okhttp

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * A REST interface for GitHub
 *
 * @author Fumihiko Shiroyama (fu.shiroyama@gmail.com)
 */

interface GitHubService {
  @GET("users/{user}/repos")
  fun listRepos(@Path("user") user: String): Single<List<Repo>>
}
