package kurly.android.pre.assignment.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kurly.android.pre.assignment.data.di.IoDispatcher
import javax.inject.Inject

class WishlistRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) {

    val wishlist: Flow<Result<Set<String>>> = dataStore.data
        .map { preferences ->
            runCatching { preferences[WISHLIST_KEY] ?: emptySet() }
        }
        .flowOn(dispatcher)

    suspend fun toggle(productId: String) {
        withContext(context = dispatcher) {
            dataStore.edit { preferences ->
                val current = preferences[WISHLIST_KEY] ?: emptySet()
                preferences[WISHLIST_KEY] = if (productId in current) {
                    current - productId
                } else {
                    current + productId
                }
            }
        }
    }

    companion object {
        private val WISHLIST_KEY = stringSetPreferencesKey("wishlist")
    }
}