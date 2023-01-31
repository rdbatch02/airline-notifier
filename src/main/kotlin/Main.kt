
import io.ktor.client.*
import io.ktor.client.engine.java.*
import kotlinx.coroutines.runBlocking
import rss.RssAggregator
import util.DateWindow

fun main(args: Array<String>) {
    val httpClient = HttpClient(Java)
    runBlocking {
        val records = RssAggregator.filteredRssData(listOf("Delta", "Skyteam"), DateWindow.today())

        println(records)
    }
}