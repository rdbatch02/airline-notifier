package rss

import rss.sources.AeroRoutes
import util.DateWindow
import util.containsAny

object RssAggregator {
    private suspend fun getAllSourceFeeds(): List<Record> {
        val sources = listOf(AeroRoutes)
        return sources.flatMap { it.getRecords() }
    }

    private fun applyFilters(keywords: List<String>?, dateRange: DateWindow?, sourceData: List<Record>): List<Record> {
        var filteredData = sourceData.toMutableList()
        if (keywords != null) {
            filteredData = filteredData.filter {
                it.title.containsAny(keywords, ignoreCase = true) || it.body.containsAny(keywords, ignoreCase = true)
            }.toMutableList()
        }
        if (dateRange != null) {
            filteredData = filteredData.filter { it.date.isAfter(dateRange.startDate) && it.date.isBefore(dateRange.endDate)  }.toMutableList()
        }
        return filteredData
    }

    suspend fun filteredRssData(keywords: List<String>? = null, dateRange: DateWindow? = null): List<Record> {
        return applyFilters(keywords, dateRange, getAllSourceFeeds())
    }
}