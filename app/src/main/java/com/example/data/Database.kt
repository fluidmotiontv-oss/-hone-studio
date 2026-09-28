package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "creator_profile")
data class CreatorProfile(
    @PrimaryKey val id: Int = 0,
    val harmonyNotes: Int = 0,
    val shavings: Int = 0,
    val crewName: String = "Stoke Creator"
)

@Dao
interface CreatorDao {
    @Query("SELECT * FROM creator_profile WHERE id = 0")
    fun getProfile(): Flow<CreatorProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProfile(profile: CreatorProfile)
}

@Database(entities = [CreatorProfile::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun creatorDao(): CreatorDao
}
