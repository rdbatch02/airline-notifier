
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent
import config.ConfigurationHelper
import kotlinx.coroutines.runBlocking
import notification.SesClient
import rss.RssAggregator
import rss.sources.AeroRoutes
import util.DateWindow

fun main() {
    lambdaHandler(null)
}

fun lambdaHandler(args: ScheduledEvent?) {
    println(args)
    runBlocking {
        val sources = listOf(AeroRoutes)
        val records = RssAggregator.filteredRssData(sources, ConfigurationHelper.filterKeywords, DateWindow.lastDay())
        if (records.isNotEmpty()) {
            println("Building response email...")
            val bodyString = records.joinToString { it.body + "<br/>URL: " + it.url }

            SesClient.sendMessage(
                body = bodyString,
                recipient = ConfigurationHelper.sendToAddresses
            )
            println("Email sent!")
        }
        else {
            println("No alerts for today!")
        }
    }
}