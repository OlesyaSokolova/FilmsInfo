package ru.nsu.fit.sokolova.filmsinfo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.nsu.fit.sokolova.filmsinfo.data.local.entity.FilmInfoEntity


@Database(
	entities = [FilmInfoEntity::class], version = 1
)

@TypeConverters
abstract class FilmsDatabase : RoomDatabase() {
	abstract val dao: FilmInfoDao
}