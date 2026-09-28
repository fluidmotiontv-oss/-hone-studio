package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.CreatorProfile
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "studio_ai_db"
    ).build()

    private val creatorDao = db.creatorDao()

    val profile = creatorDao.getProfile().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    init {
        viewModelScope.launch {
            // Initialize with default if empty
            if (profile.value == null) {
                creatorDao.updateProfile(CreatorProfile(harmonyNotes = 1240, shavings = 42))
            }
        }
    }

    fun addHarmony(amount: Int) {
        viewModelScope.launch {
            val current = profile.value ?: CreatorProfile()
            creatorDao.updateProfile(current.copy(harmonyNotes = current.harmonyNotes + amount))
        }
    }
}
