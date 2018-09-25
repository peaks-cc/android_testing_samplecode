package cc.peaks.androidtestingbible.mvp.view

import cc.peaks.androidtestingbible.retrofit_okhttp.Repo

interface View {
  fun showRepositoryList(list: List<Repo>)
}