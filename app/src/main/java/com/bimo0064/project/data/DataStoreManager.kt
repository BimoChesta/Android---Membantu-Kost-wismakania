package com.bimo0064.project.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bimo0064.project.model.DayData
import com.bimo0064.project.model.Pembayaran
import com.bimo0064.project.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "day_data_store")

class DataStoreManager(private val context: Context) {

    private val saldoKey = intPreferencesKey("saldo")
    private val paymentsKey = stringPreferencesKey("payments")

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
        val type = TypeToken.getParameterized(Map::class.java, String::class.java, DayData::class.java).type
        return Gson().fromJson(json, type)
    }

    suspend fun saveBalance(saldo: Int) {
        context.dataStore.edit { preferences ->
            preferences[saldoKey] = saldo
        }
    }

    suspend fun loadBalance(): Int {
        val preferences = context.dataStore.data.first()
        return preferences[saldoKey] ?: 0
    }

    suspend fun savePayment(pembayaran: Pembayaran) {
        context.dataStore.edit { preferences ->
            val existingPaymentsJson = preferences[paymentsKey] ?: ""
            val paymentsList = if (existingPaymentsJson.isEmpty()) {
                mutableListOf()
            } else {
                Gson().fromJson(existingPaymentsJson, Array<Pembayaran>::class.java).toMutableList()
            }
            paymentsList.add(pembayaran)
            preferences[paymentsKey] = Gson().toJson(paymentsList)
        }
    }

    suspend fun loadPayments(): List<Pembayaran> {
        val preferences = context.dataStore.data.first()
        val paymentsJson = preferences[paymentsKey] ?: return emptyList()
        return Gson().fromJson(paymentsJson, Array<Pembayaran>::class.java).toList()
    }

    private val userKey = stringPreferencesKey("user")

    suspend fun saveUser(user: User) {
        context.dataStore.edit { preferences ->
            preferences[userKey] = Gson().toJson(user)
        }
    }

    suspend fun loadUser(): User? {
        val preferences = context.dataStore.data.first()
        val userJson = preferences[userKey] ?: return null
        return Gson().fromJson(userJson, User::class.java)
    }

    private fun generateKey(month: String, year: String): String {
        return "${month}_${year}"
    }

    suspend fun clearUser() {
        context.dataStore.edit { preferences ->
            preferences.remove(userKey)
        }
    }
}
