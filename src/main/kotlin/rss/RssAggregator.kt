package rss

import rss.sources.Source
import util.DateWindow
import util.containsAny

object RssAggregator {
    private suspend fun getAllSourceFeeds(sources: List<Source>): List<Record> {
        return sources.flatMap { it.getRecords() }
    }

    private fun applyFilters(sourceData: List<Record>, keywords: List<String>?, dateRange: DateWindow?): List<Record> {
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

    suspend fun filteredRssData(sources: List<Source>, keywords: List<String>? = null, dateRange: DateWindow? = null): List<Record> {
        return applyFilters(getAllSourceFeeds(sources), keywords, dateRange)
    }
}