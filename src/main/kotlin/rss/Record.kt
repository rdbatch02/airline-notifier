package rss

import tw.ktrssreader.kotlin.model.item.RssStandardItem
import java.time.Instant

data class Record(
    val title: String,
    val date: Instant,
    val body: String,
    val url: String
) {
    companion object {
        fun fromRssStandard(rssRecord: RssStandardItem, dateFormatter: ((date: String) -> Instant) = {Instant.parse(it)}): Record {
            return Record(
                title = rssRecord.title.orEmpty(),
                date = if (!rssRecord.pubDate.isNullOrEmpty()) {
                    dateFormatter(rssRecord.pubDate!!)
                } else Instant.now(),
                body = rssRecord.description.orEmpty(),
                url = rssRecord.link.orEmpty()
                )
        }
    }
}
