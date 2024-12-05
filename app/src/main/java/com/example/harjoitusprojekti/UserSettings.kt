package com.example.harjoitusprojekti

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val city: String,
)

@Dao
interface UserSettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserSettings(userSettings: UserSettings)

    @Query("SELECT * FROM user_settings WHERE id = 0")
    suspend fun getUserSettings(): UserSettings?
}

@Database(entities = [UserSettings::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userSettingsDao(): UserSettingsDao
}

class UserSettingsRepository(private val dao: UserSettingsDao) {
    suspend fun saveUserSettings(settings: UserSettings) {
        dao.saveUserSettings(settings)
    }

    suspend fun getUserSettings(): UserSettings? {
        return dao.getUserSettings()
    }
}

class UserSettingsViewModel(private val repository: UserSettingsRepository) : ViewModel() {
    private val _userSettings = MutableStateFlow(UserSettings(name = "", city = ""))
    val userSettings get() = _userSettings.asStateFlow()

    init {
        viewModelScope.launch {
            val settings = repository.getUserSettings()
            if (settings != null) {
                _userSettings.value = settings
            }
        }
    }

    fun saveUserSettings(name: String, city: String) {
        val updatedSettings = UserSettings(id = 0, name = name, city = city)
        _userSettings.value = updatedSettings

        viewModelScope.launch {
            repository.saveUserSettings(updatedSettings)
        }
    }
}
