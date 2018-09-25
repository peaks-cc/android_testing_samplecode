package cc.peaks.androidtestingbible.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Repository::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun repositoryDao(): RepositoryDao
}