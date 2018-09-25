package cc.peaks.androidtestingbible.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface RepositoryDao {
  @Insert
  fun insertAll(vararg repositories: Repository)

  @Query("SELECT * FROM repository WHERE owner = :owner")
  fun findByOwner(owner: String): List<Repository>

  @Query("SELECT * FROM repository WHERE owner = :owner")
  fun findByOwnerRx(owner: String): Single<List<Repository>>
}