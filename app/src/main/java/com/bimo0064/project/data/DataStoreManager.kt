package com.bimo0064.project.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bimo0064.project.model.DayData
import com.google.gson.Gson
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "day_data_store")

class DataStoreManager(private val context: Context) {

    suspend fun saveDayData(month: String, year: String, dayDataMap: Map<String, DayData>) {
        val key = generateKey(month, year)
        val json = Gson().toJson(dayDataMap)
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = json
        }
    }

    suspend fun loadDayData(month: String, year: String): Map<String, DayData> {
        val key = generateKey(month, year)
        val preferences = context.dataStore.data.first()
        val json = preferences[stringPreferencesKey(key)] ?: return emptyMap()
        val type = com.google.gson.reflect.TypeToken.getParameterized(
            Map::class.java,
            String::class.java,
            DayData::class.java
        ).type
        return Gson().fromJson(json, type)
    }

    private fun generateKey(month: String, year: String): String {
        return "${month}_${year}"
    }
}