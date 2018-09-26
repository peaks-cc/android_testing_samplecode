package cc.peaks.androidtestingbible.mvp.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import cc.peaks.androidtestingbible.R

class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
  val repoName: TextView

  init {
    repoName = itemView?.findViewById(R.id.repoName) ?: throw RuntimeException()
  }
}