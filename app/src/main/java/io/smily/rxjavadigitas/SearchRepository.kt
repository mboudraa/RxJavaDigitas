package io.smily.rxjavadigitas

import android.content.Context
import android.net.ConnectivityManager
import org.threeten.bp.ZonedDateTime


class SearchRepository private constructor(private val networkRepository: SearchNetworkRepository) {

    companion object {
        fun create(context: Context, activateFailure: Boolean = false): SearchRepository {
            return SearchRepository(SearchNetworkRepository(SearchApi(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager,
                                                                      activateFailure)))
        }
    }

    fun search(text: String, now: ZonedDateTime = ZonedDateTime.now()): SearchResponse {
        return networkRepository.search(text, now)
    }

}


class SearchNetworkRepository(private val searchApi: SearchApi) {

    fun search(text: String, now: ZonedDateTime): SearchResponse {
        val validity = now.plusSeconds(30)
        return searchApi.search(text, validity).let { SearchResponse(it, validity) }
    }
}

class SearchCacheRepository(private val cache: MemoryCache) {
    fun search(text: String, now: ZonedDateTime = ZonedDateTime.now()): SearchResponse {
        return cache.getItem(text)!!
    }

    fun put(text: String, searchResponse: SearchResponse) {
        cache.add(text, searchResponse, searchResponse.validity.toInstant().toEpochMilli())
    }
}
