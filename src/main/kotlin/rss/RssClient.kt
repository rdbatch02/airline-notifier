package rss

import io.ktor.client.*
import io.ktor.client.engine.java.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

internal class RssClient(private val httpClient: HttpClient = HttpClient(Java)) {
    suspend fun getFeedData(feedUrl: String): String {
        return httpClient.get(feedUrl).bodyAsText()
    }
}