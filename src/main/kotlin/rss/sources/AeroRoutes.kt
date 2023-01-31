package rss.sources

import rss.Record
import rss.RssClient
import tw.ktrssreader.kotlin.parser.RssStandardParser
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object AeroRoutes: Source {
    private const val url = "https://www.aeroroutes.com/?format=rss"
    private suspend fun getRawFeed(): String {
        return RssClient().getFeedData(url)
    }
    private fun dateFormatter(datetime: String): Instant {
        val formatter = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss Z")
        return LocalDateTime.parse(datetime, formatter).toInstant(ZoneOffset.UTC)
    }
    override suspend fun getRecords(): List<Record> {
        return RssStandardParser().parse(getRawFeed()).items!!.map { Record.fromRssStandard(it, ::dateFormatter) }
    }
}