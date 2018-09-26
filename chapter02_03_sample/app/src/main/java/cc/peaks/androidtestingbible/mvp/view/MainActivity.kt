package cc.peaks.androidtestingbible.mvp.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import cc.peaks.androidtestingbible.R
import cc.peaks.androidtestingbible.mvp.model.GitHubRepository
import cc.peaks.androidtestingbible.mvp.model.LocalDataSource
import cc.peaks.androidtestingbible.mvp.presenter.ListPresenter
import cc.peaks.androidtestingbible.retrofit_okhttp.Repo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View {
  private lateinit var adapter: Adapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    adapter = Adapter(this)

    recyclerView.apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(this@MainActivity)
      adapter = this@MainActivity.adapter
    }

    val repository = GitHubRepository(LocalDataSource())
    val presenter = ListPresenter(this, repository)
    presenter.getRepositoryList("shiroyama")
  }

  override fun showRepositoryList(list: List<Repo>) {
    Log.i("MainActivity", list.toString())
    adapter.updateList(list)
  }
}
