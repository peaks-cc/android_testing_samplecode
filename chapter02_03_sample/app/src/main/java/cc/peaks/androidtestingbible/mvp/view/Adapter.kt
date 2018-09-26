package cc.peaks.androidtestingbible.mvp.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import cc.peaks.androidtestingbible.R
import cc.peaks.androidtestingbible.retrofit_okhttp.Repo

class Adapter : RecyclerView.Adapter<ViewHolder> {
  private val inflater: LayoutInflater
  private var list: List<Repo>

  constructor(context: Context) : super() {
    inflater = LayoutInflater.from(context)
    list = listOf()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = inflater.inflate(R.layout.list_row_repos, parent, false)
    return ViewHolder(view)
  }

  override fun getItemCount(): Int {
    return list.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val repo = list[position]
    holder.repoName.text = repo.fullName
  }

  fun updateList(list: List<Repo>) {
    this.list = list
    notifyDataSetChanged()
  }
}