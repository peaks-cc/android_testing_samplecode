package cc.peaks.androidtestingbible.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "repository")
data class Repository(
    @PrimaryKey
    val id: Long,
    val name: String, val description: String,
    @ColumnInfo(index = true)
    val owner: String
)