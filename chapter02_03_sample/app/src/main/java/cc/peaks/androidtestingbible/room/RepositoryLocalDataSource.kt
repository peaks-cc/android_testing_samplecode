package cc.peaks.androidtestingbible.room

import io.reactivex.Single

class RepositoryLocalDataSource(val db: AppDatabase) {
  fun insertAll(vararg repositories: Repository) {
    db.repositoryDao().insertAll(*repositories)
  }

  fun findByOwner(owner: String): List<Repository> {
    return db.repositoryDao().findByOwner(owner)
  }

  fun findByOwnerRx(owner: String): Single<List<Repository>> {
    return db.repositoryDao().findByOwnerRx(owner)
  }
}